import {Component, inject, OnInit} from '@angular/core';
import {Patient} from '../../../models/Patient';
import {PatientService} from '../../../services/patient-service';
import {PageResponse} from '../../../models/PageResponse';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'patients-table',
  imports: [
    DatePipe
  ],
  templateUrl: './patients-table.html',
  styleUrl: './patients-table.css'
})
export class PatientsTable implements OnInit{
  patientService = inject(PatientService);

  patients: Patient[] = [];
  totalElements = 0;
  page = 0;
  size = 10;
  doctorId = 1;

  ngOnInit() {
    this.loadPatients();
  }

  loadPatients(page: number = this.page) {
    this.patientService.getPatientsByDoctorId(this.doctorId, page, this.size)
      .subscribe((res: PageResponse<Patient>) => {
        this.patients = res.content;
        this.totalElements = res.totalElements;
        this.page = res.number;
      });
  }

  onPageChange(newPage: number) {
    this.loadPatients(newPage);
  }
}
