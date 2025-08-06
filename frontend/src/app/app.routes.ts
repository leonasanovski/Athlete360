import {Routes} from '@angular/router';
import {MoodComponent} from './components/mood/mood.component';
import {MoodDetailsComponent} from './components/mood.details/mood.details.component';
import {MoodFormComponent} from './components/mood-form-component/mood-form-component';

export const routes: Routes = [
  {path: 'moods', component: MoodComponent},
  {path: 'moods/add-mood', component: MoodFormComponent},
  {path: 'moods/:id', component: MoodComponent},
  {path: 'moods/info/:id', component: MoodDetailsComponent},
  {path: '', redirectTo: 'moods', pathMatch: 'full'}
];
