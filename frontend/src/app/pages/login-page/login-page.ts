import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../../services/auth-service';
import {of} from 'rxjs';
import {switchMap, catchError, finalize, take} from 'rxjs/operators';


@Component({
  selector: 'login-page',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css'
})
export class LoginPageComponent {
  private auth = inject(AuthService);
  private router = inject(Router);
  loading = false;
  serverError = '';

  formGroup: FormGroup = new FormGroup({
    embg: new FormControl('1104003450027', [Validators.required, Validators.minLength(13), Validators.maxLength(13)]),
    password: new FormControl('p@ssw0rd', [Validators.required, Validators.minLength(8)]),
  });
  //todo - this shall be fixed so when someoene with unfinished account loggs in, to redirect him to finish and create patient/doctor entity and then to allow him the usage of the app
  submit(): void {
    this.serverError = '';
    if (this.formGroup.invalid || this.loading) return;
    console.log('Before new values inserted: ', this.formGroup)
    const {embg, password} = this.formGroup.getRawValue();
    this.loading = true;
    console.log('After new values: ', this.formGroup.getRawValue())
    this.auth.login(embg, password).pipe(
      switchMap(() => {
        const user = this.auth.getCurrentUser();
        console.log(`User Info: ${user}`)
        if (!user) throw new Error('No user session after login');
        const role = user.role as 'DOCTOR' | 'PATIENT' | 'PENDING' | 'ADMIN';
        if (role === 'DOCTOR') {
          if (user.personId) {
            console.log('')
            this.router.navigate(['/doctor']);
          } else {
            this.router.navigate(['/doctor/setup']);
          }
          return of(null);
        }

        if (role === 'PATIENT') {
          this.router.navigate(['/patient']);
          return of(null);
        }

        if (role === 'PENDING') {
          this.router.navigate(['/pending']);
          return of(null);
        }

        // ADMIN or anything else
        this.router.navigate(['/']);
        return of(null);
      }),
      catchError(err => {
        this.serverError = err?.message || 'Login failed';
        return of(null);
      }),
      finalize(() => (this.loading = false))
    ).subscribe();
  }
}
