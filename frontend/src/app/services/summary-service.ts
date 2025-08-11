import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, of, throwError} from 'rxjs';
import {Summary} from '../models/Summary';

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
}
