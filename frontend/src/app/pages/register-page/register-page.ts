import {Component, inject, OnInit} from '@angular/core';
import {AuthService} from '../../core/services/auth-service';
import {Router} from '@angular/router';
import {
  FormControl,
  FormGroup,
  Validators,
  AbstractControl,
  ValidationErrors,
  ValidatorFn,
  ReactiveFormsModule, FormsModule
} from '@angular/forms';
import {Gender} from '../../models/types/Gender';

@Component({
  selector: 'app-register-page',
  imports: [
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './register-page.html',
  styleUrl: './register-page.css'
})
export class RegisterPage implements OnInit {
  private auth = inject(AuthService);
  private router = inject(Router);
  genders: string[] = ['MALE', 'FEMALE'] as const;
  showPass: boolean = false

  formGroup: FormGroup = new FormGroup({
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      embg: new FormControl('', validateEmbg('dateOfBirth', 'gender')),
      dateOfBirth: new FormControl('', Validators.required),
      gender: new FormControl<Gender | null>(null, {validators: [Validators.required]}),
      password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*\d)(?=.*[^A-Za-z0-9])[a-z\d!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]{8,}$/)]),
      confirmPassword: new FormControl('', [Validators.required, Validators.minLength(8)]),
      email: new FormControl('', [Validators.required, Validators.email])
    },
    {validators: passwordMatchValidator('password', 'confirmPassword')},
  );

  get f() {
    return this.formGroup.controls as any;
  }

  onSubmit(): void {
    if (this.formGroup.invalid) return;
    const formValues = this.formGroup.getRawValue()
    this.auth.register(formValues).subscribe({
      next: () => {
        const user = this.auth.getCurrentUser();
        this.router.navigate(['/pending']);
      },
      error: (err: unknown) => {
        console.error(`Error: ${err}`)
      }
    })
  }

  ngOnInit() {
    this.formGroup.get('dateOfBirth')!.valueChanges.subscribe(() =>
      this.formGroup.get('embg')!.updateValueAndValidity({onlySelf: true})
    );
    this.formGroup.get('gender')!.valueChanges.subscribe(() =>
      this.formGroup.get('embg')!.updateValueAndValidity({onlySelf: true})
    );
  }

  showPassword() {
    this.showPass =
      !this.showPass
  }
}


export function passwordMatchValidator(passwordCtrl = 'password', confirmCtrl = 'confirmPassword'): ValidatorFn {
  return (group: AbstractControl): ValidationErrors | null => {
    const pass = group.get(passwordCtrl)?.value;
    const confirm = group.get(confirmCtrl)?.value;
    if (!pass || !confirm) return null;
    return pass === confirm ? null : {error: 'Passwords are not matching'};
  };
}

export function validateEmbg(dateOfBirthCtrlName = 'dateOfBirth', genderCtrlName = 'gender'): ValidatorFn {
  const regex = /^\d{7}(450|455)\d{3}$/;
  return (embgControl: AbstractControl): ValidationErrors | null => {
    const embgValue = String(embgControl?.value ?? '').trim();
    if (!regex.test(embgValue)) {
      return {error: 'EMBG must be ddmmyyy followed by 450xxx (male) or 455xxx (female), and the end with 3 numbers'}
    }
    const dd = parseInt(embgValue.slice(0, 2), 10);
    const mm = parseInt(embgValue.slice(2, 4), 10);
    const yyy = parseInt(embgValue.slice(4, 7), 10);
    const year = yyy < 100 ? 2000 + yyy : 1000 + yyy;
    if (!isValidDate(dd, mm, year)) {
      return {error: 'Invalid date encoded in EMBG'};
    }
    const parent = embgControl.parent;
    if (parent) {
      const dobCtrl = parent.get(dateOfBirthCtrlName);
      if (dobCtrl?.value) {
        const dob = dobCtrl.value instanceof Date ? dobCtrl.value : new Date(dobCtrl.value);
        if (!Number.isNaN(dob.getTime())) {
          const same =
            dob.getFullYear() === year &&
            (dob.getMonth() + 1) === mm &&
            dob.getDate() === dd;

          if (!same) {
            return {error: 'Date of birth does not match EMBG'};
          }
        }
      }
      const genderCtrl = parent.get(genderCtrlName);
      if (genderCtrl?.value) {
        const genderVal = genderCtrl.value.toUpperCase();
        const gCode = embgValue.slice(7, 10);

        if ((gCode === '450' && genderVal !== 'MALE') ||
          (gCode === '455' && genderVal !== 'FEMALE')) {
          return {error: 'Gender does not match EMBG'};
        }
      }
    }
    return null;
  }

  function isValidDate(d: number, m: number, y: number): boolean {
    if (m < 1 || m > 12 || d < 1 || d > 31) return false;
    const dt = new Date(y, m - 1, d);
    return dt.getFullYear() === y && dt.getMonth() === m - 1 && dt.getDate() === d;
  }
}
