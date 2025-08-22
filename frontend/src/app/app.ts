import {Component, OnDestroy, OnInit, inject} from '@angular/core';
import {Router, NavigationEnd, RouterOutlet} from '@angular/router';
import {filter, map, startWith, takeUntil, combineLatestWith} from 'rxjs';
import {Subject} from 'rxjs';
import {Sidebar} from './components/sidebar/sidebar';
import {AuthService} from './services/auth-service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Sidebar],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  private router = inject(Router);
  private auth = inject(AuthService);
  role: string | undefined;
  hideSidebar = true;

  ngOnInit() {
    const route$ = this.router.events.pipe(
      filter(e => e instanceof NavigationEnd),
      map((e: any) => e.urlAfterRedirects ?? e.url ?? ''),
      startWith(this.router.url || '')
    );
    this.auth.currentUser$
      .pipe(
        startWith(this.auth.getCurrentUser()),
        combineLatestWith(route$),
      )
      .subscribe(([user, url]) => {
        this.role = user?.role;
        const isDoctorOrPatient = this.role === 'DOCTOR' || this.role === 'PATIENT';
        const onAuthPage =
          url.startsWith('/login') ||
          url.startsWith('/login') ||
          url.startsWith('/doctor/setup') ||
          url.startsWith('/patient/setup');
        this.hideSidebar = onAuthPage || !isDoctorOrPatient;
      });
  }
}
