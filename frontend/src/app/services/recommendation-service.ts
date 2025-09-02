import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Recommendation} from '../models/Recommendation';
import {catchError, Observable, of} from 'rxjs';
import {RecommendationFormDTO} from '../models/RecommendationFormDTO';

@Injectable({
  providedIn: 'root'
})
export class RecommendationService {
  http = inject(HttpClient);
  url = "http://localhost:8080/api";

  getRecommendationsByReportId(id: number): Observable<Recommendation[]> {
    return this.http.get<Recommendation[]>(`${this.url}/reports/${id}/recommendations`);
  }

  getLatestRecommendations(id: number): Observable<Recommendation[]> {
    return this.http
      .get<Recommendation[]>(`${this.url}/patient/${id}/latest/recommendations`).pipe(
        catchError(() => of([]))
      )
  }

  createRecommendation(recommendation: RecommendationFormDTO): Observable<number> {
    return this.http.post<number>(`${this.url}/recommendations`, recommendation);
  }
}
