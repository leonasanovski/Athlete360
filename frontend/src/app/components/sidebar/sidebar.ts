import {Component, inject, Input} from '@angular/core';
import {Router, RouterLink, RouterLinkActive} from '@angular/router';
import {AuthService} from '../../core/services/auth-service';

@Component({
  selector: 'sidebar',
  imports: [
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './sidebar.html',
  standalone: true,
  styleUrl: './sidebar.css'
})
export class Sidebar{
  @Input() role: String | undefined;
  authService = inject(AuthService);
  router = inject(Router);
  loggedUser = this.authService.getCurrentUser()!

  onLogout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
