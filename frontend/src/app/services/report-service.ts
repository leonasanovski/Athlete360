import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, switchMap, take, tap} from 'rxjs';
import {ReportShort} from '../models/ReportShort';
import {ReportDetails} from '../models/ReportDetails';
import {AuthService} from './auth-service';
import {PageResponse} from '../models/PageResponse';
import {ReportForm} from '../models/ReportForm';
import {ReportFlags} from '../models/ReportFlags';

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  auth = inject(AuthService);
  http = inject(HttpClient);
  url = "http://localhost:8080/api";

  getReportsShort(
    page: number = 0,
    size: number = 10,
    sort: string = 'createdAt,desc',
    id: number | null = null,
  ): Observable<PageResponse<ReportShort>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort);

    return id==null ? this.auth.currentUser$.pipe(
      take(1),
      switchMap(user => {

        return this.http.get<PageResponse<ReportShort>>(
          `${this.url}/${user?.role.toLowerCase()}/${user?.id}/reports`,
          {params}
        )
      })
    ) : this.http.get<PageResponse<ReportShort>>(
      `${this.url}/patient/${id}/reports`,
      { params }
    )
  }

  getReportById(id: number): Observable<ReportDetails> {
    return this.http.get<ReportDetails>(`${this.url}/reports/${id}`);
  }

  getFlagsForAttributes(id: number): Observable<ReportFlags> {
    return this.http.get<ReportFlags>(`${this.url}/reports/${id}/flags`);
  }

  createReport(report: ReportForm): Observable<number> {
    return this.http.post<number>(`${this.url}/reports`, report);
  }

  updateReport(id: number, report: ReportForm): Observable<void> {
    return this.http.put<void>(`${this.url}/reports/${id}`, report);
  }
}
