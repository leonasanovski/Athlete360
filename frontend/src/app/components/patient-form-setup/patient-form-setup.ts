import {Component, inject} from '@angular/core';
import {AuthService} from '../../services/auth-service';
import {DoctorService} from '../../services/doctor-service';
import {Router} from '@angular/router';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {PatientService} from '../../services/patient-service';
import {Gender} from '../../models/types/Gender';
import {SportsmanCategory} from '../../models/types/SportsmanCategory';
import {CreatePatientDTO} from '../../models/dto/CreatePatientDTO';

@Component({
  selector: 'app-patient-form-setup',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './patient-form-setup.html',
  styleUrl: './patient-form-setup.css'
})
export class PatientFormSetup {
  auth = inject(AuthService)
  loggedUser = this.auth.getCurrentUser()
  patientService = inject(PatientService)
  router = inject(Router)
  constructor() {
    console.log('PATIENT FORM SETUP BLABLABLA')
  }
  genders: string[] = ['MALE', 'FEMALE'] as const;
  categories: string[] = ['RECREATION', 'AMATEUR', 'SEMI_PROFESSIONAL', 'PROFESSIONAL'] as const;
  formGroup = new FormGroup({
    dateOfBirth: new FormControl<string | null>(null, { validators: [Validators.required] }),
    gender: new FormControl<Gender | null>(null, { validators: [Validators.required] }),
    sportsmanCategory: new FormControl<SportsmanCategory | null>(null, { validators: [Validators.required] }),
  });

  submit() {
    if (this.formGroup.invalid) return;
    const { dateOfBirth, gender, sportsmanCategory } = this.formGroup.getRawValue();
    if (!dateOfBirth || !gender || !sportsmanCategory) return;
    const payload: CreatePatientDTO = {
      dateOfBirth,
      gender,
      sportsmanCategory
    };
    console.log('Sega sme pred da povikame servisot na angular')
    this.patientService.savePatientEntity(payload).subscribe({
      next: () => {
        this.auth.logout()
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.log('Error creating patient profile:', err);
      }
    });
  }


}
