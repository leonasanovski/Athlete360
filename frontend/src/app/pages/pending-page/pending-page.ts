import {Component, inject} from '@angular/core';
import {AuthService} from '../../services/auth-service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-pending-page',
  imports: [],
  templateUrl: './pending-page.html',
  styleUrl: './pending-page.css'
})
export class PendingPage {
  authService = inject(AuthService)
  pendingUser = this.authService.getCurrentUser()
  router = inject(Router)
  logoutUser(){
    this.authService.logout()
    this.router.navigate(['/login'])
  }
}
