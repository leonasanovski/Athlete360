import {Routes} from '@angular/router';
import {MoodComponent} from './components/mood/mood.component';
import {PatientPage} from './pages/patient-page/patient-page';
import {ReportDetailsPage} from './pages/report-details-page/report-details-page';

export const routes: Routes = [
  {path: 'moods', component: MoodComponent},
  {path: 'patient', component: PatientPage},
  {path: 'reports', component: ReportDetailsPage},
  {path: '', redirectTo: 'patient', pathMatch: 'full'},
  {path: '**', redirectTo: 'patient'}
];
