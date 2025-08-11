import { Injectable } from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {CurrentUser} from '../models/CurrentUser';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _currentUser$ = new BehaviorSubject<CurrentUser>({id: 1, role: 'doctor'});
  currentUser$ = this._currentUser$.asObservable();

  getCurrentUser(): CurrentUser {
    return this._currentUser$.value;
  }

  setMockUser(u: CurrentUser) {
    this._currentUser$.next(u);
  }
}
