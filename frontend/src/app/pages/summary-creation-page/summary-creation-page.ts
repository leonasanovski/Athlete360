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
  styleUrl: './summary-creation-page.css'
})
export class SummaryCreationPage implements OnInit{
  route = inject(ActivatedRoute);
  router = inject(Router);

  summaryService = inject(SummaryService);

  reportId: number | undefined;
  summarizedContent: string | undefined;

  formGroup: FormGroup = new FormGroup({
    reportId: new FormControl(0, Validators.required),
    summarizedContent: new FormControl('', Validators.required)
  })

  ngOnInit(): void {
    this.route.paramMap.pipe(
      map(pm => pm.get('reportId')),
      filter(reportId => reportId != null),
      mergeMap(reportId => {
        this.reportId = +reportId;
        this.formGroup.patchValue({reportId: +reportId});
        return this.summaryService.createSummaryAI(+reportId).pipe(
          mergeMap(reportId => this.summaryService.getSummaryByReportId(reportId))
        )
      })
    ).subscribe(summary => {
      this.summarizedContent = summary?.summarizedContent
      this.formGroup.patchValue({summarizedContent: this.summarizedContent});
    })
  }

  onSubmit() {
    if(this.reportId && this.summarizedContent) {
      this.summaryService.patchSummary(this.reportId, this.formGroup.value);
    }
  }
}
