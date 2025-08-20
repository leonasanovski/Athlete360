import {Component, inject, OnInit} from '@angular/core';
import {NavigationEnd, Router, RouterOutlet} from '@angular/router';
import {Sidebar} from './components/sidebar/sidebar';
import {filter, first} from 'rxjs';
import {AuthService} from './services/auth-service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Sidebar],
  templateUrl: './app.html',
  standalone: true,
  styleUrl: './app.css'
})
export class App implements OnInit {
  auth = inject(AuthService);
  router = inject(Router);
  role: String | undefined;
  ngOnInit() {
    console.log('app started')
  }
}
