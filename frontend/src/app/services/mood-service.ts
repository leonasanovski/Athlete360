import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Mood} from '../models/mood';

@Injectable({
  providedIn: 'root'
})
export class MoodService {
  http = inject(HttpClient)
  private mood_url = 'http://localhost:8080/api/mood'
  getAllMoods(): Observable<Mood[]> {
    return this.http.get<Mood[]>(this.mood_url);
  }
  getMoodById(id: number): Observable<MoodService | null> {
    const url = `${this.mood_url}/${id}`
    return this.http.get<MoodService>(url);
  }
}
