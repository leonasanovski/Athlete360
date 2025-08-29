import {Component, inject, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth-service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-role-redirect',
  imports: [],
  template: ''
})
export class RoleRedirect implements OnInit{
  private auth = inject(AuthService);
  private router = inject(Router);
  ngOnInit(): void {
    const role = this.auth.getRole();
    switch (role) {
      case 'ADMIN':
        this.router.navigate(['/admin']);
        break;
      case 'DOCTOR':
        this.router.navigate(['/doctor']);
        break;
      case 'PATIENT':
        this.router.navigate(['/patient']);
        break;
      case 'PENDING':
        this.router.navigate(['/pending']);
        break;
      default:
        this.router.navigate(['/login']);
        break;
    }
  }
}
