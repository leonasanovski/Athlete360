import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {MoodComponent} from './components/mood/mood.component';
import {MoodFormComponent} from './components/mood-form-component/mood-form-component';
import {Sidebar} from './components/sidebar/sidebar';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MoodComponent, RouterLink, Sidebar, MoodFormComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'frontend';
}
