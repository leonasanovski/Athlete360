import {Component, inject, OnInit} from '@angular/core';
import {NavigationEnd, Router, RouterOutlet} from '@angular/router';
import {Sidebar} from './components/sidebar/sidebar';
import {filter, first} from 'rxjs';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Sidebar],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  router = inject(Router);

  role = 'doctor'; // or 'doctor'

  ngOnInit() {
    this.router.events
      .pipe(
        filter(event => event instanceof NavigationEnd),
        first() // only run on the first completed navigation
      )
      .subscribe((event: NavigationEnd) => {
        if (event.urlAfterRedirects === '/' || event.urlAfterRedirects === '') {
          if (this.role === 'patient') {
            this.router.navigate(['/patient']);
          } else if (this.role === 'doctor') {
            this.router.navigate(['/doctor']);
          }
        }
      });
  }
}
