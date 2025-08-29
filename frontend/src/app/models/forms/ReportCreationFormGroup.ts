import {FormControl, FormGroup, Validators} from '@angular/forms';

export const reportCreationFormGroup = new FormGroup({
  doctorId: new FormControl(0, Validators.required),
  embg: new FormControl('', [
    Validators.required,
    Validators.minLength(13),
    Validators.maxLength(13)
  ]),
  status: new FormControl('GOOD', Validators.required),

  vo2Max: new FormControl('', [
    Validators.required,
    Validators.min(0),
    Validators.max(999.99),
    Validators.pattern(/^\d{1,3}(\.\d{1,2})?$/)
  ]),
  restingHeartRate: new FormControl('', [
    Validators.required,
    Validators.min(20),
    Validators.max(250),
    Validators.pattern(/^\d+$/)
  ]),
  underPressureHeartRate: new FormControl('', [
    Validators.required,
    Validators.min(50),
    Validators.max(250),
    Validators.pattern(/^\d+$/)
  ]),

  bodyFatPercentage: new FormControl('', [
    Validators.required,
    Validators.min(0),
    Validators.max(100),
    Validators.pattern(/^\d{1,3}(\.\d)?$/)
  ]),
  leanMuscleMass: new FormControl('', [
    Validators.min(0),
    Validators.pattern(/^\d{1,3}(\.\d)?$/)
  ]),
  boneDensity: new FormControl('', [
    Validators.required,
    Validators.min(0),
    Validators.max(99.99),
    Validators.pattern(/^\d{1,2}(\.\d{1,2})?$/)
  ]),
  height: new FormControl('', [
    Validators.required,
    Validators.min(50),
    Validators.max(300),
    Validators.pattern(/^\d{2,3}(\.\d)?$/)
  ]),
  weight: new FormControl('', [
    Validators.required,
    Validators.min(2),
    Validators.max(500),
    Validators.pattern(/^\d{1,3}(\.\d)?$/)
  ]),

  oneRepMaxBench: new FormControl('', [
    Validators.min(0),
    Validators.pattern(/^\d{1,3}(\.\d)?$/)
  ]),
  oneRepMaxSquat: new FormControl('', [
    Validators.min(0),
    Validators.pattern(/^\d{1,3}(\.\d)?$/)
  ]),
  oneRepMaxDeadlift: new FormControl('', [
    Validators.min(0),
    Validators.pattern(/^\d{1,3}(\.\d)?$/)
  ]),
  jumpHeight: new FormControl('', [
    Validators.min(0),
    Validators.pattern(/^\d{1,3}(\.\d)?$/)
  ]),
  averageRunPerKilometer: new FormControl('', [
    Validators.required,
    Validators.min(60),
    Validators.max(99.99),
    Validators.pattern(/^\d{1,2}(\.\d{1,2})?$/)
  ]),

  shoulderFlexibility: new FormControl('', [
    Validators.min(0),
    Validators.max(360),
    Validators.pattern(/^\d{1,3}$/)
  ]),
  hipFlexibility: new FormControl('', [
    Validators.min(0),
    Validators.max(360),
    Validators.pattern(/^\d{1,3}$/)
  ]),
  balanceTime: new FormControl('', [
    Validators.required,
    Validators.min(0),
    Validators.max(99.9),
    Validators.pattern(/^\d{1,2}(\.\d)?$/)
  ]),
  reactionTime: new FormControl('', [
    Validators.required,
    Validators.min(0),
    Validators.max(99.999),
    Validators.pattern(/^\d{1,2}(\.\d{1,3})?$/)
  ]),
  coreStabilityScore: new FormControl('', [
    Validators.required,
    Validators.min(0),
    Validators.max(100),
    Validators.pattern(/^\d{1,3}$/)
  ]),

  hemoglobin: new FormControl('', [
    Validators.required,
    Validators.min(0),
    Validators.max(99.9),
    Validators.pattern(/^\d{1,2}(\.\d)?$/)
  ]),
  glucose: new FormControl('', [
    Validators.required,
    Validators.min(0),
    Validators.max(999.9),
    Validators.pattern(/^\d{1,3}(\.\d)?$/)
  ]),
  creatinine: new FormControl('', [
    Validators.required,
    Validators.min(0),
    Validators.max(99.99),
    Validators.pattern(/^\d{1,2}(\.\d{1,2})?$/)
  ]),
  vitaminD: new FormControl('', [
    Validators.required,
    Validators.min(0),
    Validators.max(99.9),
    Validators.pattern(/^\d{1,2}(\.\d)?$/)
  ]),
  iron: new FormControl('', [
    Validators.required,
    Validators.min(0),
    Validators.max(999.9),
    Validators.pattern(/^\d{1,3}(\.\d)?$/)
  ]),
  testosterone: new FormControl('', [
    Validators.required,
    Validators.min(0),
    Validators.max(999.9),
    Validators.pattern(/^\d{1,3}(\.\d)?$/)
  ]),
  cortisol: new FormControl('', [
    Validators.required,
    Validators.min(0),
    Validators.max(99.9),
    Validators.pattern(/^\d{1,2}(\.\d)?$/)
  ])
});
