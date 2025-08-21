import {Component, inject} from '@angular/core';
import {AuthService} from '../../services/auth-service';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {DoctorService} from '../../services/doctor-service';
import {Router} from '@angular/router';
import {log} from '@angular-devkit/build-angular/src/builders/ssr-dev-server';

@Component({
  selector: 'app-doctor-form-setup',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './doctor-form-setup.html',
  styleUrl: './doctor-form-setup.css'
})
export class DoctorFormSetup {
  auth = inject(AuthService)
  loggedUser = this.auth.getCurrentUser()
  doctorService = inject(DoctorService)
  router = inject(Router)
  formGroup: FormGroup = new FormGroup({
    specialization: new FormControl('', [Validators.required]),
    userId: new FormControl(this.loggedUser?.userId),
  });

  submit() {
    if (this.formGroup.invalid) return;
    const formData = this.formGroup.getRawValue()
    this.doctorService.saveDoctorEntity(formData)
      .subscribe({
        next: (resp) => {
          this.auth.logout()
          this.router.navigate(['/login']);
        },
        error: (error) => {
          console.log('Error creating doctor profile:', error);
        }
      });
  }

  protected readonly log = log;
}
