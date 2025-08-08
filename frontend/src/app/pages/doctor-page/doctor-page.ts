import { Component } from '@angular/core';
import {PatientsTable} from '../../components/patient/patients-table/patients-table';
import {ReportsTable} from '../../components/report/reports-table/reports-table';

@Component({
  selector: 'doctor-page',
  imports: [
    PatientsTable,
    ReportsTable
  ],
  templateUrl: './doctor-page.html',
  styleUrl: './doctor-page.css'
})
export class DoctorPage {

}
