import {Routes} from '@angular/router';
import {MoodComponent} from './components/mood/mood.component';
import {PatientPage} from './pages/patient-page/patient-page';
import {ReportDetailsPage} from './pages/report-details-page/report-details-page';
import {ReportsPage} from './pages/reports-page/reports-page';
import {MoodFormComponent} from './components/mood-form-component/mood-form-component';
import {MoodDetailsComponent} from './components/mood.details/mood.details.component';

export const routes: Routes = [
  {path: 'moods', component: MoodComponent},
  {path: 'moods/add-mood', component: MoodFormComponent},
  {path: 'moods/:id', component: MoodComponent},
  {path: 'moods/info/:id', component: MoodDetailsComponent},

  {path: 'patient', component: PatientPage},

  {path: 'reports', component: ReportsPage},
  {path: 'reports/:id', component: ReportDetailsPage},

  {path: '', redirectTo: 'patient', pathMatch: 'full'},
  {path: '**', redirectTo: 'patient'}
];
