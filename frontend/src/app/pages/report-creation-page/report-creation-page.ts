import {Component, inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ReportService} from '../../services/report-service';
import {ActivatedRoute, Router} from '@angular/router';
import {ReportForm} from '../../models/ReportForm';
import {AthleteReportStatus} from '../../models/types/AthleteReportStatus';
import {AuthService} from '../../services/auth-service';
import {filter, mergeMap} from 'rxjs';
import {ReportDetails} from '../../models/ReportDetails';

@Component({
  selector: 'report-creation-page',
  imports: [ReactiveFormsModule],
  templateUrl: './report-creation-page.html',
  standalone: true,
  styleUrl: './report-creation-page.css'
})
export class ReportCreationPage implements OnInit{
  authService = inject(AuthService);
  reportService = inject(ReportService);
  router = inject(Router);
  route = inject(ActivatedRoute);
  report: ReportForm | undefined;
  statuses: AthleteReportStatus[] = ['GOOD', 'IMPROVED', 'FOLLOWUP'];

  formGroup: FormGroup = new FormGroup({
    doctorId: new FormControl(0, Validators.required),
    embg: new FormControl('', Validators.required),
    status: new FormControl('GOOD', Validators.required),
    vo2Max: new FormControl('', Validators.required),
    restingHeartRate: new FormControl('', Validators.required),
    underPressureHeartRate: new FormControl('', Validators.required),
    bodyFatPercentage: new FormControl('', Validators.required),
    leanMuscleMass: new FormControl(''),
    boneDensity: new FormControl('', Validators.required),
    height: new FormControl('', Validators.required),
    weight: new FormControl('', Validators.required),
    oneRepMaxBench: new FormControl(''),
    oneRepMaxSquat: new FormControl(''),
    oneRepMaxDeadlift: new FormControl(''),
    jumpHeight: new FormControl(''),
    averageRunPerKilometer: new FormControl('', Validators.required),
    shoulderFlexibility: new FormControl(''),
    hipFlexibility: new FormControl(''),
    balanceTime: new FormControl('', Validators.required),
    reactionTime: new FormControl('', Validators.required),
    coreStabilityScore: new FormControl('', Validators.required),
    hemoglobin: new FormControl('', Validators.required),
    glucose: new FormControl('', Validators.required),
    creatinine: new FormControl('', Validators.required),
    vitaminD: new FormControl('', Validators.required),
    iron: new FormControl('', Validators.required),
    testosterone: new FormControl('', Validators.required),
    cortisol: new FormControl('', Validators.required)
  });

  ngOnInit(): void {
    const doctorId = this.authService.getCurrentUser().id;
    this.formGroup.patchValue({ doctorId });

    this.route.paramMap.pipe(
      filter(params => params.has('id')),
      mergeMap(params => this.reportService.getReportById(+params.get('id')!!))
    ).subscribe(report =>{
      if(report) {
        this.report = this.mapDetailsToForm(report, doctorId)
        this.formGroup.patchValue(this.report);
      }
    })
  }

  onSubmit() {}

  mapDetailsToForm(details: ReportDetails, doctorId: number): ReportForm {
    console.log(details.embg)
    return {
      doctorId,
      embg: details.embg,
      status: details.status,
      vo2Max: details.vo2Max,
      restingHeartRate: details.restingHeartRate,
      underPressureHeartRate: details.underPressureHeartRate,
      bodyFatPercentage: details.bodyFatPercentage,
      leanMuscleMass: details.leanMuscleMass ?? undefined,
      boneDensity: details.boneDensity,
      height: details.height,
      weight: details.weight,
      oneRepMaxBench: details.oneRepMaxBench ?? undefined,
      oneRepMaxSquat: details.oneRepMaxSquat ?? undefined,
      oneRepMaxDeadlift: details.oneRepMaxDeadlift ?? undefined,
      jumpHeight: details.jumpHeight ?? undefined,
      averageRunPerKilometer: details.averageRunPerKilometer,
      shoulderFlexibility: details.shoulderFlexibility ?? undefined,
      hipFlexibility: details.hipFlexibility ?? undefined,
      balanceTime: details.balanceTime,
      reactionTime: details.reactionTime,
      coreStabilityScore: details.coreStabilityScore,
      hemoglobin: details.hemoglobin,
      glucose: details.glucose,
      creatinine: details.creatinine,
      vitaminD: details.vitaminD,
      iron: details.iron,
      testosterone: details.testosterone,
      cortisol: details.cortisol
    };
  }
}
