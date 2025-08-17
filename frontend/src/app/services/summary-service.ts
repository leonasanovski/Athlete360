import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, of, throwError} from 'rxjs';
import {Summary} from '../models/Summary';
import {SummaryForm} from '../models/SummaryForm';

@Injectable({
  providedIn: 'root'
})
export class SummaryService {
  http = inject(HttpClient);
  url = "http://localhost:8080/api";

  getSummaryByReportId(id: number) : Observable<Summary | null> {
    return this.http.get<Summary>(`${this.url}/reports/${id}/summary`).pipe(
      catchError((err: HttpErrorResponse) => {
        if (err.status === 404) {
          return of(null);
        }
        return throwError(() => err);
      })
    );
  }

  getSummaryAI(reportId: number): Observable<string> {
    return this.http.get(`${this.url}/summary/ai/${reportId}`, { responseType: 'text' });
  }

  patchSummary(reportId: number, payload: Partial<SummaryForm>): Observable<number> {
    return this.http.patch<number>(`${this.url}/summary/${reportId}`, payload);
  }

  createSummary(form: SummaryForm): Observable<number> {
    return this.http.post<number>(`${this.url}/summary`, form);
  }
}
