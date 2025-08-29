import {Component, inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ReportService} from '../../services/report-service';
import {ActivatedRoute, Router} from '@angular/router';
import {ReportForm} from '../../models/ReportForm';
import {AthleteReportStatus} from '../../models/types/AthleteReportStatus';
import {AuthService} from '../../services/auth-service';
import {debounceTime, distinctUntilChanged, filter, mergeMap, of, switchMap} from 'rxjs';
import {ReportDetails} from '../../models/ReportDetails';
import {Patient} from '../../models/Patient';
import {PatientService} from '../../services/patient-service';
import {reportCreationFormGroup} from '../../models/forms/ReportCreationFormGroup';
import {NgIf} from '@angular/common';

@Component({
  selector: 'report-creation-page',
  imports: [ReactiveFormsModule, NgIf],
  templateUrl: './report-creation-page.html',
  standalone: true,
  styleUrl: './report-creation-page.css'
})
export class ReportCreationPage implements OnInit{
  authService = inject(AuthService);
  reportService = inject(ReportService);
  patientService = inject(PatientService);
  patientType = false;
  router = inject(Router);
  route = inject(ActivatedRoute);
  report: ReportForm | undefined;
  reportId: number | null = null;

  fields = [
    { name: 'vo2Max', label: 'VO₂ Max', type: 'number', step: 0.01, required: true, min: 0, max: 100 },
    { name: 'restingHeartRate', label: 'Resting Heart Rate', type: 'number', step: 1, required: true, min: 20, max: 250 },
    { name: 'underPressureHeartRate', label: 'Under Pressure Heart Rate', type: 'number', step: 1, required: true, min: 50, max: 250 },
    { name: 'bodyFatPercentage', label: 'Body Fat %', type: 'number', step: 0.1, required: true, min: 0, max: 100 },
    { name: 'leanMuscleMass', label: 'Lean Muscle Mass', type: 'number', step: 0.1, min: 0, max: 100 },
    { name: 'boneDensity', label: 'Bone Density', type: 'number', step: 0.01, required: true, min: 0, max: 5 },
    { name: 'height', label: 'Height', type: 'number', step: 0.1, required: true, min: 50, max: 300 },
    { name: 'weight', label: 'Weight', type: 'number', step: 0.1, required: true, min: 2, max: 500 },
    { name: 'oneRepMaxBench', label: 'One Rep Max Bench', type: 'number', step: 0.1, min: 0, max: 500 },
    { name: 'oneRepMaxSquat', label: 'One Rep Max Squat', type: 'number', step: 0.1, min: 0, max: 1000 },
    { name: 'oneRepMaxDeadlift', label: 'One Rep Max Deadlift', type: 'number', step: 0.1, min: 0, max: 1500 },
    { name: 'jumpHeight', label: 'Jump Height', type: 'number', step: 0.1, min: 0, max: 100 },
    { name: 'averageRunPerKilometer', label: 'Average Run per KM', type: 'number', step: 0.01, required: true, min: 60, max: 1000 },
    { name: 'shoulderFlexibility', label: 'Shoulder Flexibility', type: 'number', step: 1, min: 0, max: 360 },
    { name: 'hipFlexibility', label: 'Hip Flexibility', type: 'number', step: 1, min: 0, max: 360 },
    { name: 'balanceTime', label: 'Balance Time', type: 'number', step: 0.1, required: true, min: 0, max: 100 },
    { name: 'reactionTime', label: 'Reaction Time', type: 'number', step: 0.001, required: true, min: 0, max: 100 },
    { name: 'coreStabilityScore', label: 'Core Stability Score', type: 'number', step: 1, required: true, min: 0, max: 100 },
    { name: 'hemoglobin', label: 'Hemoglobin', type: 'number', step: 0.1, required: true, min: 0, max: 100 },
    { name: 'glucose', label: 'Glucose', type: 'number', step: 0.1, required: true, min: 0, max: 100 },
    { name: 'creatinine', label: 'Creatinine', type: 'number', step: 0.01, required: true, min: 0, max: 100 },
    { name: 'vitaminD', label: 'Vitamin D', type: 'number', step: 0.1, required: true, min: 0, max: 100 },
    { name: 'iron', label: 'Iron', type: 'number', step: 0.1, required: true, min: 0, max: 100 },
    { name: 'testosterone', label: 'Testosterone', type: 'number', step: 0.1, required: true, min: 0, max: 100 },
    { name: 'cortisol', label: 'Cortisol', type: 'number', step: 0.1, required: true, min: 0, max: 100 }
  ];

  formGroup: FormGroup = reportCreationFormGroup;

  embgResults: Patient[] = [];
  showEmbgDropdown = false;
  private hideDropdownTimeout: any;

  ngOnInit(): void {
    const doctorId: number | null = this.authService.getCurrentUser()?.personId ?? null;
    this.formGroup.patchValue({ doctorId });

    this.route.paramMap.pipe(
      filter(params => params.has('id')),
      mergeMap(params => this.reportService.getReportById(+params.get('id')!!))
    ).subscribe(report =>{
      if(report && doctorId) {
        this.report = this.mapDetailsToForm(report, doctorId);
        this.reportId = report.reportId;
        this.formGroup.patchValue(this.report);
      }
    })

    this.route.queryParamMap.pipe(
      filter(params => params.has('embg')),
    ).subscribe(params => {
      this.formGroup.patchValue({embg: params.get('embg')})
    })

    this.formGroup.get('embg')!.valueChanges.pipe(
      debounceTime(400),
      distinctUntilChanged(),
      filter(value => {
        const condition = value && value.length >= 3 && value.length!=13;
        if(!condition) this.showEmbgDropdown = false;
        return condition
      }),
      switchMap(query => {
        this.showEmbgDropdown = true;
        console.log('Query:', query)
        console.log('Patient type: ', this.patientType ? 'New' : 'Old')
        return this.patientService.searchPatientsByEmbg(query, this.patientType)
      })
    ).subscribe(res => {
      this.embgResults = res.content;
    });
  }

  onSubmit() {
    if(this.report) {
      this.reportService.updateReport(this.reportId!!, this.formGroup.value);
    } else {
      this.reportService.createReport(this.formGroup.value)
        .subscribe(id => {
          this.router.navigate(['/recommendations/new'], {
            queryParams: { reportId: id }
          });
        })
    }
  }

  selectEmbg(embg: string) {
    this.formGroup.patchValue({ embg });
    this.showEmbgDropdown = false;
  }

  hideDropdownAfterDelay() {
    this.hideDropdownTimeout = setTimeout(() => {
      this.showEmbgDropdown = false;
    }, 200);
  }

  mapDetailsToForm(details: ReportDetails, doctorId: number): ReportForm {
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
