import {inject, Injectable} from '@angular/core';
import {AuthService} from '../core/services/auth-service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {CreateDoctorDTO} from '../models/dto/CreateDoctorDTO';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {
  auth = inject(AuthService);
  http = inject(HttpClient);
  url = "http://localhost:8080/api/doctor";

  saveDoctorEntity(doctorData: CreateDoctorDTO): Observable<any> {
    const token = this.auth.getToken();
    if (!token) {
      return throwError(() => new Error('No token present'));
    }
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    console.log("DoctorDTO:", doctorData)
    console.log('Headers: ', headers)
    return this.http.post(`${this.url}/create-doctor-user`, doctorData, {headers});
  }
}

