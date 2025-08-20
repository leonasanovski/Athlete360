import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../../services/auth-service';
import {finalize} from 'rxjs';


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
    embg: new FormControl('0202020450345', [Validators.required, Validators.minLength(13), Validators.maxLength(13)]),
    password: new FormControl('p@ssw0rd', [Validators.required, Validators.minLength(8)]),
  });

  submit(): void {
    this.serverError = '';
    if (this.formGroup.invalid || this.loading) return;
    const {embg, password} = this.formGroup.getRawValue();
    this.loading = true;
    this.auth.login(embg, password)
      .pipe(finalize(() => this.loading = false))
      .subscribe({
        next: () => {
          const user = this.auth.getCurrentUser()
          const role = user?.role
          if (role === 'DOCTOR') {
            this.router.navigate(['/doctor'])
          } else if (role === 'PATIENT') {
            this.router.navigate(['/patient']);
          }
        },
        error: (err: Error) => {
          this.loading = false;
          this.serverError = err.message || 'Login failed';
        }
      });
  }
}
