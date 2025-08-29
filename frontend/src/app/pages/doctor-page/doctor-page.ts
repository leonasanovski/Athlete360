import {Component, inject, OnInit} from '@angular/core';
import {PatientsTable} from '../../components/patient-components/patients-table/patients-table';
import {ReportsTable} from '../../components/report-components/reports-table/reports-table';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {AuthService} from '../../core/services/auth-service';
import {CurrentUser} from '../../models/CurrentUser';

@Component({
  selector: 'doctor-page',
  imports: [
    PatientsTable,
    ReportsTable,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './doctor-page.html',
  standalone: true,
  styleUrl: './doctor-page.css'
})
export class DoctorPage implements OnInit {
  authService = inject(AuthService);
  currentUser: CurrentUser | null = null;


  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
  }
}
