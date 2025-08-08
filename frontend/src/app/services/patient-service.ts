import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {PageResponse} from '../models/PageResponse';
import {Patient} from '../models/Patient';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  http = inject(HttpClient);
  url = "http://localhost:8080/api";

  getPatientsByDoctorId(
    doctorId: number,
    page: number = 0,
    size: number = 10,
    sort: string = 'name,desc'
  ): Observable<PageResponse<Patient>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort);

    return this.http.get<PageResponse<Patient>>(
      `${this.url}/doctor/${doctorId}/patients`,
      { params }
    );
  }
}
