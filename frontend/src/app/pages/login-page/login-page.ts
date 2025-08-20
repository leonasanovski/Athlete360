import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import {AuthService} from '../../services/auth-service';


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
    embg: new FormControl('', [Validators.required, Validators.minLength(13), Validators.maxLength(13)]),
    password: new FormControl('', [Validators.required, Validators.minLength(8)]),
  });

  submit(): void {
    this.serverError = '';
    if (this.formGroup.invalid || this.loading) return;

    const { embg, password } = this.formGroup.getRawValue();
    this.loading = true;

    this.auth.login(embg, password).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/']);
      },
      error: (err: Error) => {
        this.loading = false;
        this.serverError = err.message || 'Login failed';
      }
    });
  }
}
