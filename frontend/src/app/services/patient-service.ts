import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {filter, Observable, switchMap, take} from 'rxjs';
import {PageResponse} from '../models/PageResponse';
import {Patient} from '../models/Patient';
import {AuthService} from './auth-service';

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
      filter(user => user.role != 'patient'),
      switchMap(user => {
        const params = new HttpParams()
          .set('page', page.toString())
          .set('size', size.toString())
          .set('sort', sort);
        return this.http.get<PageResponse<Patient>>(
          `${this.url}/doctor/${user.id}/patients`,
          { params }
        )
      })
    );
  }

  searchPatientsByEmbg(
    embg: string,
    page: number = 0,
    size: number = 10,
    sort: string = 'name,asc'
  ): Observable<PageResponse<Patient>> {
    return this.auth.currentUser$.pipe(
      take(1),
      filter(user => user.role != 'patient'),
      switchMap(user => {
        const params = new HttpParams()
          .set('embg', embg)
          .set('page', page.toString())
          .set('size', size.toString())
          .set('sort', sort);
        return this.http.get<PageResponse<Patient>>(
          `${this.url}/doctor/${user.id}/patients/search`,
          { params }
        )
      })
    )
  }
}
