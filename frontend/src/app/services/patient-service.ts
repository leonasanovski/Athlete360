import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {filter, Observable, switchMap, take, throwError} from 'rxjs';
import {PageResponse} from '../models/PageResponse';
import {Patient} from '../models/Patient';
import {AuthService} from '../core/services/auth-service';
import {CreatePatientDTO} from '../models/dto/CreatePatientDTO';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  auth = inject(AuthService);
  http = inject(HttpClient);
  url = "http://localhost:8080/api";

  getPatients(
    page: number = 0,
    size: number = 10,
    sort: string = 'name,asc'
  ): Observable<PageResponse<Patient>> {
    return this.auth.currentUser$.pipe(
      take(1),
      filter(user => user?.role != 'PATIENT'),
      switchMap(user => {
        const params = new HttpParams()
          .set('page', page.toString())
          .set('size', size.toString())
          .set('sort', sort);
        return this.http.get<PageResponse<Patient>>(
          `${this.url}/doctor/${user?.personId}/patients`,
          {params}
        )
      })
    );
  }

  searchPatientsByEmbg(
    embg: string,
    patientType?: boolean | null,
    page: number = 0,
    size: number = 10,
    sort: string = 'name,asc'
  ): Observable<PageResponse<Patient>> {
    return this.auth.currentUser$.pipe(
      take(1),
      filter(user => user?.role != 'PATIENT'),
      switchMap(user => {
        let params = new HttpParams()
          .set('embg', embg)
          .set('page', page.toString())
          .set('size', size.toString())
          .set('sort', sort)
        if (patientType === undefined || patientType === null) {
          params = params.set('patientType', false)
        } else {
          params = params.set('patientType', String(patientType))
        }
        if (patientType === true) {
          return this.http.get<PageResponse<Patient>>(
            `${this.url}/doctor/${user?.personId}/patients/search`,
            {params}
          )
        } else {
          return this.http.get<PageResponse<Patient>>(
            `${this.url}/doctor/${user?.personId}/patients/search`,
            {params}
          )
        }
      })
    )
  }

  getPatientById(id: number): Observable<Patient> {
    const url = `${this.url}/patient/${id}`
    return this.http.get<Patient>(url)
  }

  savePatientEntity(patientData: CreatePatientDTO): Observable<any> {
    const token = this.auth.getToken();
    if (!token) {
      return throwError(() => new Error('No token present'));
    }
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    return this.http.post(`${this.url}/patient/create-patient-user`, patientData, {headers});

  }
}
