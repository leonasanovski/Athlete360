import {Component, inject} from '@angular/core';
import {AuthService} from '../../services/auth-service';

@Component({
  selector: 'app-pending-page',
  imports: [],
  templateUrl: './pending-page.html',
  styleUrl: './pending-page.css'
})
export class PendingPage {
  authService = inject(AuthService)
  pendingUser = this.authService.getCurrentUser()
}
