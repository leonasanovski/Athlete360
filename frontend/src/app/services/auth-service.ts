import {inject, Injectable} from '@angular/core';
import {BehaviorSubject, catchError, map, Observable, throwError} from 'rxjs';
import {CurrentUser} from '../models/CurrentUser';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {jwtDecode} from 'jwt-decode';
import {JwtPayload} from '../models/JWTPayload';
import {UserRole} from '../models/types/UserRole';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  http = inject(HttpClient);
  private readonly TOKEN_KEY = 'auth_token';
  private readonly API_BASE = 'http://localhost:8080/api';
  private _currentUser$ = new BehaviorSubject<CurrentUser | null>(null);
  currentUser$ = this._currentUser$.asObservable();

  constructor() {
    const token = this.getToken();
    if (token) this.setCurrentUserFromToken(token);
  }

  login(embg: string, password: string): Observable<string> {
    return this.http
      .post(`${this.API_BASE}/login`, { embg, password }, { responseType: 'text' })
      .pipe(
        map((tokenText: string) => {
          const token = (tokenText || '').trim();
          if (!token) throw new Error('Invalid login response');
          this.setToken(token);
          this.setCurrentUserFromToken(token);
          return token;
        }),
        catchError((err: HttpErrorResponse) =>
          throwError(() => new Error(err.error?.message || 'Login failed'))
        )
      );
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    this._currentUser$.next(null);
  }

  getRole(): UserRole | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      const decoded = jwtDecode<JwtPayload>(token);
      return decoded.role ?? null;
    } catch (e) {
      console.error('Invalid token', e);
      return null;
    }
  }

  getCurrentUser(): CurrentUser | null {
    return this._currentUser$.value;
  }

  isLoggedIn(): boolean {
    return !!this.getToken()
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  private setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  private setCurrentUserFromToken(token: string): void {
    try {
      const payload = jwtDecode<JwtPayload>(token);
      if (payload.exp * 1000 <= Date.now()) {
        this.logout();
        return;
      }
      const currentUser: CurrentUser = {
        personId: payload.personId ?? null,
        userId: payload.userId,
        embg: payload.sub,
        role: payload.role,
        firstName: payload.firstName,
        lastName: payload.lastName,
        exp: payload.exp
      };
      this._currentUser$.next(currentUser);
    } catch {
      this.logout();
    }
  }

}
