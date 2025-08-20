import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {SummaryService} from '../../services/summary-service';
import {filter, map, mergeMap} from 'rxjs';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';

@Component({
  selector: 'summary-creation-page',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './summary-creation-page.html',
  standalone: true,
  styleUrl: './summary-creation-page.css'
})
export class SummaryCreationPage implements OnInit{
  route = inject(ActivatedRoute);
  router = inject(Router);
  summaryService = inject(SummaryService);

  formGroup: FormGroup = new FormGroup({
    reportId: new FormControl(0, Validators.required),
    summarizedContent: new FormControl('', Validators.required)
  })

  reportId: number | undefined;
  hasSummary = false;
  saving = false;

  aiLoading = false;
  aiResult: string | null = null;
  aiError: string | null = null;

  successMessage: string | null = null;
  errorMessage: string | null = null;

  ngOnInit(): void {
    this.route.paramMap.pipe(
      map(pm => pm.get('reportId')),
      filter(reportId => reportId != null),
      mergeMap(reportId => {
        this.reportId = +reportId;
        this.formGroup.patchValue({reportId: +reportId});
        return this.summaryService.getSummaryByReportId(+reportId);
      })
    ).subscribe({
      next: summary => {
        if (summary) {
          this.hasSummary = true;
          this.formGroup.patchValue({ summarizedContent: summary.summarizedContent ?? '' });
        } else {
          this.hasSummary = false;
        }
      },
      error: err => {
        console.error('Error fetching summary by report id', err);
      }
    })
  }

  generateSummary() {
    this.clearMessages();
    if (!this.reportId) {
      this.aiError = 'No report id found.';
      return;
    }

    this.aiLoading = true;
    this.aiResult = null;
    this.aiError = null;

    this.summaryService.getSummaryAI(this.reportId).subscribe({
      next: (res: string) => {
        this.aiResult = res ?? '';
        this.aiLoading = false;
      },
      error: (err) => {
        console.error('AI generation error', err);
        this.aiError = 'Failed to generate summary. Try again.';
        this.aiLoading = false;
      }
    });
  }

  applyAiToTextarea(replace = true) {
    if (!this.aiResult) return;

    const current = this.formGroup.get('summarizedContent')?.value ?? '';
    const newVal = replace ? this.aiResult : (current ? `${current}\n\n${this.aiResult}` : this.aiResult);
    this.formGroup.patchValue({ summarizedContent: newVal });
  }

  copyAiToClipboard() {
    if (!this.aiResult) return;
    navigator.clipboard?.writeText(this.aiResult).then(
      () => { this.successMessage = 'AI text copied to clipboard.'; setTimeout(()=> this.successMessage = null, 2500); },
      () => { this.errorMessage = 'Could not copy to clipboard.'; setTimeout(()=> this.errorMessage = null, 2500); }
    );
  }

  clearMessages() {
    this.successMessage = null;
    this.errorMessage = null;
    this.aiError = null;
  }

  onSubmit() {
    this.clearMessages();

    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      this.errorMessage = 'Please fix the form errors before submitting.';
      setTimeout(()=> this.errorMessage = null, 3000);
      return;
    }

    const payload = this.formGroup.value;
    if (!this.reportId) {
      this.errorMessage = 'Report id missing.';
      return;
    }

    this.saving = true;

    if (this.hasSummary) {
      this.summaryService.patchSummary(this.reportId, payload).subscribe({
        next: (resp) => {
          this.saving = false;
          this.successMessage = 'Summary updated successfully.';
          setTimeout(() => this.successMessage = null, 3000);
        },
        error: (err) => {
          console.error('Patch summary error', err);
          this.saving = false;
          this.errorMessage = 'Failed to update summary.';
          setTimeout(() => this.errorMessage = null, 4000);
        }
      });
    } else {
      this.summaryService.createSummary(payload).subscribe({
        next: (newId) => {
          this.saving = false;
          this.hasSummary = true;
          this.successMessage = 'Summary created successfully.';
          setTimeout(() => this.successMessage = null, 3000);
        },
        error: (err) => {
          console.error('Create summary error', err);
          this.saving = false;
          this.errorMessage = 'Failed to create summary.';
          setTimeout(() => this.errorMessage = null, 4000);
        }
      });
    }
  }
}
