import {Routes} from '@angular/router';
import {MoodComponent} from './components/mood/mood.component';

export const routes: Routes = [
  {path: 'moods', component: MoodComponent},
  {path: '', redirectTo: 'moods', pathMatch: 'full'}
];
