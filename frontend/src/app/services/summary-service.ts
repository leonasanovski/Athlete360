import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Summary} from '../models/Summary';

@Injectable({
  providedIn: 'root'
})
export class SummaryService {
  http = inject(HttpClient);
  url = "http://localhost:8080/api";

  getSummaryByReportId(id: number) : Observable<Summary> {
    return this.http.get<Summary>(`${this.url}/reports/${id}/summary`);
  }
}
