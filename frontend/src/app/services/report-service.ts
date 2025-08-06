import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, tap} from 'rxjs';
import {ReportShort} from '../models/ReportShort';
import {ReportDetails} from '../models/ReportDetails';

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  http = inject(HttpClient);
  url = "http://localhost:8080/api";

  getReportsShortByPatientId(id: number): Observable<ReportShort[]> {
    return this.http.get<ReportShort[]>(`${this.url}/patient/${id}/reports`);
  }

  getReportById(id: number): Observable<ReportDetails> {
    return this.http.get<ReportDetails>(`${this.url}/reports/${id}`);
  }
}
