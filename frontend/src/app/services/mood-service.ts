import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {filter, map, Observable} from 'rxjs';
import {Mood} from '../models/mood';

@Injectable({
  providedIn: 'root'
})
export class MoodService {
  http = inject(HttpClient)
  private mood_url = 'http://localhost:8080/api/moods'
  getAllMoods(): Observable<Mood[]> {
    return this.http.get<Mood[]>(this.mood_url);
  }
  getMoodsByPatientId(id: number): Observable<Mood[]> {
    //filtering on backend
    const url = `${this.mood_url}/${id}`
    return this.http.get<Mood[]>(url);

    // filtering on frontend side
    // return this.getAllMoods().pipe(
    //   map(moods => moods.filter(mood => mood.patientId == id))
    // )
  }

  getMoodByIdFrom(moodId: number): Observable<Mood>{
    const url = `${this.mood_url}/info/${moodId}`
    return this.http.get<Mood>(url)
  }
}
