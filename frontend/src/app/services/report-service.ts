import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ReportShort} from '../models/ReportShort';

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  http = inject(HttpClient);
  url = "http://localhost:8080/api";

  getReportsShortByPatientId(id: number): Observable<ReportShort[]> {
    return this.http.get<ReportShort[]>(`${this.url}/patient/${id}/reports`);
  }
}
