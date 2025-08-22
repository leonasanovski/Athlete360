import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AppUserDTO} from '../models/dto/AppUserDTO';
import {PageResponse} from '../models/PageResponse';
import {UserRole} from '../models/types/UserRole';

@Injectable({ providedIn: 'root' })
export class AdminService {
  private url = 'http://localhost:8080/api/admin';
  http = inject(HttpClient);


  getPending(page = 0, size = 20, sort = 'createdAt,desc', embg?: string): Observable<PageResponse<AppUserDTO>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort)
    if (embg) params = params.set('embg', embg)

    return this.http.get<PageResponse<AppUserDTO>>(`${this.url}/get-all-pending`, { params })
  }


  updateUserRole(id: number, role: UserRole) {
    const params = new HttpParams().set('role', role)
    return this.http.patch<AppUserDTO>(`${this.url}/users/${id}/role`, null, { params })
  }


  deleteUser(id: number) {
    return this.http.delete<void>(`${this.url}/users/${id}`)
  }
}
