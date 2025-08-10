import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Mood, MoodResponse} from '../models/Mood';
import {MoodStatisticsDTO} from '../models/MoodStatistics';
import {Page} from '../models/Page';

@Injectable({
  providedIn: 'root'
})
export class MoodService {
  http = inject(HttpClient)
  private mood_url = 'http://localhost:8080/api/moods'

  getMoodByIdFrom(moodId: number): Observable<Mood> {
    const url = `${this.mood_url}/info/${moodId}`
    return this.http.get<Mood>(url)
  }

  createMood(moodObject: MoodResponse): Observable<any> {
    return this.http.post(this.mood_url, moodObject)
  }

  getMoodStatisticsForPatient(id: number): Observable<MoodStatisticsDTO> {
    console.log(id)
    const url = `${this.mood_url}/${id}/statistics`
    return this.http.get<MoodStatisticsDTO>(url)
  }


  filterSearch(
    id: number,
    from?: string,
    to?: string,
    moodEmotion?: string[],
    moodProgress?: string[],
    pageSize: number = 4,
    pageNumber: number = 1
  ): Observable<Page<Mood>> {
    let params = new HttpParams()
      .set('pageSize', pageSize)
      .set('pageNumber', pageNumber)
    params.set('patientId', id)
    if (from) {
      params.set('from', from)
    }
    if (to) {
      params.set('to', to)
    }
    if (moodEmotion && moodEmotion.length != 0) {
      moodEmotion.forEach(emotion => params = params.append('moodEmotion', emotion))
    }
    if (moodProgress && moodProgress.length != 0) {
      moodProgress.forEach(progress => params = params.append('moodProgress', progress))
    }
    const url = `${this.mood_url}/${id}/search`
    return this.http.get<Page<Mood>>(url, {params});
  }
}
