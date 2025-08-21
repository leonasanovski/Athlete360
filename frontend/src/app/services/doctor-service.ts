import {inject, Injectable} from '@angular/core';
import {AuthService} from './auth-service';
import {HttpClient} from '@angular/common/http';
import {catchError, map, Observable, of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {
  auth = inject(AuthService);
  http = inject(HttpClient);
  url = "http://localhost:8080/api/doctor";

  saveDoctorEntity(doctorData: any): Observable<any> {
    return this.http.post(`${this.url}/create-doctor-user`, doctorData);
  }
}
//TODO TESTIRANJE
