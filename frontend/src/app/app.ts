import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {MoodComponent} from './components/mood/mood.component';
import {Sidebar} from './components/sidebar/sidebar';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MoodComponent, RouterLink, Sidebar],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
}
