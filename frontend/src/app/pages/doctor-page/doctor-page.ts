import {Component, inject, OnInit} from '@angular/core';
import {PatientsTable} from '../../components/patient/patients-table/patients-table';
import {ReportsTable} from '../../components/report/reports-table/reports-table';
import {Observable, of, shareReplay} from 'rxjs';
import {ReportShort} from '../../models/ReportShort';
import {ReportService} from '../../services/report-service';

@Component({
  selector: 'doctor-page',
  imports: [
    PatientsTable,
    ReportsTable
  ],
  templateUrl: './doctor-page.html',
  standalone: true,
  styleUrl: './doctor-page.css'
})
export class DoctorPage {
}
