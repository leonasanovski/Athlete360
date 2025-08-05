import { Component } from '@angular/core';
import {ReportsTable} from '../../components/report/reports-table/reports-table';

@Component({
  selector: 'patient-page',
  imports: [
    ReportsTable
  ],
  templateUrl: './patient-page.html',
  styleUrl: './patient-page.css'
})
export class PatientPage {

}
