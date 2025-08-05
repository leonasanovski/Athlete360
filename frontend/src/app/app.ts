import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {MoodComponent} from './components/mood/mood.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MoodComponent, RouterLink],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'frontend';
}
