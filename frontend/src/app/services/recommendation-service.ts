import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Recommendation} from '../models/Recommendation';
import {Observable} from 'rxjs';
import {ReportForm} from '../models/ReportForm';
import {RecommendationForm} from '../models/RecommendationForm';

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
    return this.http.get<Recommendation[]>(`${this.url}/patient/${id}/latest/recommendations`);
  }

  createRecommendation(recommendation: RecommendationForm): Observable<number> {
    console.log(recommendation)
    return this.http.post<number>(`${this.url}/recommendations`, recommendation);
  }
}
