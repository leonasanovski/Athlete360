import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import {RecommendationService} from '../../services/recommendation-service';
import {catchError, filter, forkJoin, map, of} from 'rxjs';
import {RecommendationForm} from '../../components/recommendation/recommendation-form/recommendation-form';

@Component({
  selector: 'recommendation-creation-page',
  imports: [
    ReactiveFormsModule,
    RecommendationForm,
    FormsModule
  ],
  templateUrl: './recommendation-creation-page.html',
  standalone: true,
  styleUrl: './recommendation-creation-page.css'
})
export class RecommendationCreationPage implements OnInit {
  recommendationService = inject(RecommendationService);
  fb = inject(FormBuilder);
  router = inject(Router);
  route = inject(ActivatedRoute);
  forms: FormArray<FormGroup> = this.fb.array<FormGroup>([]);
  reportId: number | null = null;
  submitting = false;

  ngOnInit(): void {
    this.route.queryParamMap.pipe(
      map(qpm => qpm.get('reportId')),
      filter(reportId => reportId != null)
    ).subscribe(reportId => {
      this.reportId = +reportId;
      this.addForm();
    })
  }

  addForm() {
    this.forms.push(this.createRecommendationGroup());
    setTimeout(() => {
      const elems = document.getElementsByClassName('card');
      if (elems.length) {
        const el = elems[elems.length - 1] as HTMLElement;
        el.scrollIntoView({behavior: 'smooth', block: 'center'});
      }
    }, 50);
  }

  removeForm(index: number) {
    if (this.forms.length <= 1) {
      this.forms.removeAt(index);
      if (this.forms.length === 0) this.addForm();
    } else {
      this.forms.removeAt(index);
    }
  }

  cancel() {
    this.router.navigate([`/reports/${this.reportId ?? ''}`]);
  }

  onSubmit() {
    if (this.forms.invalid) {
      this.forms.markAllAsTouched();
      return;
    }
    this.submitting = true;
    const payloads = this.forms.controls.map(fg => fg.value);

    const calls = payloads.map(p =>
      this.recommendationService.createRecommendation(p).pipe(
        catchError(err => {
          console.error('createRecommendation failed for payload', p, err);
          return of(null);
        })
      )
    );

    forkJoin(calls).subscribe({
      next: (results) => {
        this.submitting = false;
        this.router.navigate([`/reports/${this.reportId}`])
      },
      error: (err) => {
        console.error('Error creating recommendations', err);
        this.submitting = false;
      }
    });
  }

  private createRecommendationGroup(): FormGroup {
    return this.fb.group({
      reportId: [this.reportId ?? 0, Validators.required],
      type: ['TRAINING', Validators.required],
      restrictionLevel: ['NORMAL', Validators.required],
      label: ['', Validators.required],
      description: ['', Validators.required],
      costPerMonth: [0, Validators.required],
      durationWeeks: [1, Validators.required],
      frequencyPerDay: [0, Validators.required],
      targetGoal: ['', Validators.required],
      effectivenessRating: [0, [Validators.required, Validators.min(0), Validators.max(10)]],
      doctorPersonalizedNotes: ['', Validators.required]
    });
  }
}
