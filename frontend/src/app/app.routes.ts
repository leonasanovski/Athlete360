import {Routes} from '@angular/router';
import {MoodComponent} from './components/mood/mood.component';
import {MoodDetailsComponent} from './components/mood.details/mood.details.component';

export const routes: Routes = [
  {path: 'moods', component: MoodComponent},
  {path: 'moods/:id', component: MoodComponent},
  {path: 'moods/info/:id', component: MoodDetailsComponent},
  {path: '', redirectTo: 'moods', pathMatch: 'full'}
];
