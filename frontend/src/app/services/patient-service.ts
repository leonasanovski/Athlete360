import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Patient} from '../models/Patient';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  http = inject(HttpClient)
  private patient_url = 'http://localhost:8080/api/patient'

  getPatientById(id: number): Observable<Patient> {
    const url = `${this.patient_url}/${id}`
    return this.http.get<Patient>(url)
  }
}
