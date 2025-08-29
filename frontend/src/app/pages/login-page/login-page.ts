import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../../services/auth-service';
import {of} from 'rxjs';
import {switchMap, catchError, finalize} from 'rxjs/operators';


@Component({
  selector: 'login-page',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
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

  submit(): void {
    this.serverError = '';
    if (this.formGroup.invalid || this.loading) return;
    const {embg, password} = this.formGroup.getRawValue();
    this.loading = true;
    this.auth.login(embg, password).pipe(
      switchMap(() => {
        const user = this.auth.getCurrentUser();
        if (!user) throw new Error('No user session after login');
        const role = user.role;
        if (role === 'DOCTOR') {
          if (user.personId) {
            this.router.navigate(['/doctor']);
          } else {
            this.router.navigate(['/doctor/setup']);
          }
          return of(null);
        }
        if (role === 'PATIENT') {
          if (user.personId) {
            this.router.navigate(['/patient']);
          } else {
            this.router.navigate(['/patient/setup']);
          }
          return of(null);
        }

        if (role === 'PENDING') {
          this.router.navigate(['/pending']);
          return of(null);
        }

        if (role === 'ADMIN') {
          this.router.navigate(['/admin']);
          return of(null);
        }
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

  goToRegister() {
    this.router.navigate(['/register']);
  }
}
