import { Component } from '@angular/core';
import {ReportsTable} from '../../components/report/reports-table/reports-table';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'patient-page',
  imports: [
    ReportsTable,
    RouterLink
  ],
  templateUrl: './patient-page.html',
  standalone: true,
  styleUrl: './patient-page.css'
})
export class PatientPage {

}
