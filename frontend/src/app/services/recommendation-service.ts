import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Recommendation} from '../models/Recommendation';
import {catchError, Observable, of} from 'rxjs';
import {ReportForm} from '../models/ReportForm';
import {RecommendationFormDTO} from '../models/RecommendationFormDTO';

@Injectable({
  providedIn: 'root'
})
export class RecommendationService {
  http = inject(HttpClient);
  url = "http://localhost:8080/api";

  getRecommendationsByReportId(id: number) : Observable<Recommendation[]> {
    return this.http.get<Recommendation[]>(`${this.url}/reports/${id}/recommendations`);
  }

  getLatestRecommendations(id: number) : Observable<Recommendation[]> {
    return this.http
      .get<Recommendation[]>(`${this.url}/patient/${id}/latest/recommendations`)
      .pipe(
        catchError(err => {
          if (err.status === 404) {
            // Patient has no recommendations yet
            return of([]); // return empty array instead of error
          }
          // rethrow other errors if needed
          throw err;
        })
      );
  }

  createRecommendation(recommendation: RecommendationFormDTO): Observable<number> {
    return this.http.post<number>(`${this.url}/recommendations`, recommendation);
  }
}
