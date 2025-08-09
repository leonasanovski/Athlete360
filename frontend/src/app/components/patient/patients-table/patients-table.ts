import {Component, inject, OnInit} from '@angular/core';
import {Patient} from '../../../models/Patient';
import {PatientService} from '../../../services/patient-service';
import {PageResponse} from '../../../models/PageResponse';
import {DatePipe} from '@angular/common';
import {debounceTime, distinctUntilChanged, Subject, switchMap} from 'rxjs';

@Component({
  selector: 'patients-table',
  imports: [
    DatePipe
  ],
  templateUrl: './patients-table.html',
  standalone: true,
  styleUrl: './patients-table.css'
})
export class PatientsTable implements OnInit{
  patientService = inject(PatientService);
  query$ = new Subject<string>();

  patients: Patient[] = [];
  totalElements = 0;
  page = 0;
  size = 10;

  sortField: string = 'name';
  sortDirection: 'asc' | 'desc' = 'asc';

  ngOnInit() {
    this.loadPatients();

    this.query$.pipe(
      debounceTime(400),
      distinctUntilChanged(),
      switchMap(query => {
        if(query) {
          return this.patientService.searchPatientsByEmbg(query)
        } else {
          return this.patientService.getPatients()
        }
      })
    ).subscribe(res => {
      this.patients = res.content;
      this.totalElements = res.totalElements;
      this.page = res.number;
    })
  }

  loadPatients(page: number = this.page) {
    const sortParam = `${this.sortField},${this.sortDirection}`;
    this.patientService.getPatients(page, this.size, sortParam)
      .subscribe(res => {
        this.patients = res.content;
        this.totalElements = res.totalElements;
        this.page = res.number;
      });
  }

  onPageChange(newPage: number) {
    this.loadPatients(newPage);
  }

  onSearch(query: string) {
    this.query$.next(query);
  }

  toggleSortByName() {
    if (this.sortField === 'name') {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = 'name';
      this.sortDirection = 'asc';
    }
    this.loadPatients(0);
  }
}
