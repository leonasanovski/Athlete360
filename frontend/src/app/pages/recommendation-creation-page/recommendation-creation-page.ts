import {Component, inject, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth-service';
import {ActivatedRoute, Router} from '@angular/router';
import {RecommendationForm} from '../../models/RecommendationForm';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {RecommendationService} from '../../services/recommendation-service';
import {filter, map} from 'rxjs';

@Component({
  selector: 'recommendation-creation-page',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './recommendation-creation-page.html',
  standalone: true,
  styleUrl: './recommendation-creation-page.css'
})
export class RecommendationCreationPage implements OnInit {
  authService = inject(AuthService);
  recommendationService = inject(RecommendationService);

  router = inject(Router);
  route = inject(ActivatedRoute);

  recommendation: RecommendationForm | undefined;
  reportId: number | null = null;

  formGroup: FormGroup = new FormGroup({
    reportId: new FormControl(0, Validators.required),
    type: new FormControl('TRAINING', Validators.required),
    restrictionLevel: new FormControl('NORMAL', Validators.required),
    label: new FormControl('', Validators.required),
    description: new FormControl('', Validators.required),
    costPerMonth: new FormControl(0, Validators.required),
    durationWeeks: new FormControl(1, Validators.required),
    frequencyPerDay: new FormControl(0, Validators.required),
    targetGoal: new FormControl('', Validators.required),
    effectivenessRating: new FormControl(0, [Validators.required, Validators.min(0), Validators.max(10)]),
    doctorPersonalizedNotes: new FormControl('', Validators.required)
  })

  ngOnInit(): void {
    this.route.queryParamMap.pipe(
      map(qpm => qpm.get('reportId')),
      filter(reportId => reportId != null)
    ).subscribe(reportId => {
      this.reportId = +reportId;
      this.formGroup.patchValue({reportId:this.reportId})
    })
  }

  onSubmit() {
    if(this.recommendation) {
      console.log("TODO: UPDATE RECOMMENDATION")
    } else {
      this.recommendationService.createRecommendation(this.formGroup.value)
        .subscribe(id => {
          this.router.navigate([`/reports/${this.reportId}`])
        })
    }
  }

}
