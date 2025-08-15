import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, of, throwError} from 'rxjs';
import {Summary} from '../models/Summary';
import {RecommendationFormDTO} from '../models/RecommendationFormDTO';
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

  createSummaryAI(reportId: number): Observable<number> {
    return this.http.get<number>(`${this.url}/summary/ai/${reportId}`);
  }

  patchSummary(reportId: number, payload: Partial<SummaryForm>): Observable<number> {
    return this.http.patch<number>(`${this.url}/summary/${reportId}`, payload);
  }
}
