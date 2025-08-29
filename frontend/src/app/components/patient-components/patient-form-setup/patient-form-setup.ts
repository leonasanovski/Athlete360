import {Component, inject} from '@angular/core';
import {AuthService} from '../../../core/services/auth-service';
import {Router} from '@angular/router';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {PatientService} from '../../../services/patient-service';
import {SportsmanCategory} from '../../../models/types/SportsmanCategory';
import {CreatePatientDTO} from '../../../models/dto/CreatePatientDTO';

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

  categories: string[] = ['RECREATION', 'AMATEUR', 'SEMI_PROFESSIONAL', 'PROFESSIONAL'] as const;
  formGroup = new FormGroup({
    sportsmanCategory: new FormControl<SportsmanCategory | null>(null, {validators: [Validators.required]})
  });

  submit() {
    if (this.formGroup.invalid) return;
    const sportsmanCategory: SportsmanCategory | null = this.formGroup.getRawValue().sportsmanCategory;
    const id: number | null = this.loggedUser?.userId ?? null
    if (!sportsmanCategory || !id) return;
    const payload: CreatePatientDTO = {
      id,
      sportsmanCategory
    };
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
