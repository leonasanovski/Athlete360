import {Routes} from '@angular/router';
import {MoodComponent} from './components/mood/mood.component';
import {PatientPage} from './pages/patient-page/patient-page';
import {ReportDetailsPage} from './pages/report-details-page/report-details-page';
import {ReportsPage} from './pages/reports-page/reports-page';
import {MoodFormComponent} from './components/mood-form-component/mood-form-component';
import {MoodDetailsComponent} from './components/mood.details/mood.details.component';
import {DoctorPage} from './pages/doctor-page/doctor-page';
import {ReportCreationPage} from './pages/report-creation-page/report-creation-page';
import {RecommendationCreationPage} from './pages/recommendation-creation-page/recommendation-creation-page';

export const routes: Routes = [
  {path: 'patient', component: PatientPage},

  {path: 'doctor', component: DoctorPage},

  {path: 'moods', component: MoodComponent},
  {path: 'moods/add-mood', component: MoodFormComponent},
  {path: 'moods/:id/search', component: MoodComponent},
  {path: 'moods/info/:id', component: MoodDetailsComponent},

  {path: 'reports', component: ReportsPage},
  {path: 'reports/new', component: ReportCreationPage},
  {path: 'reports/new/:id', component: ReportCreationPage},
  {path: 'reports/:id', component: ReportDetailsPage},

  {path: 'recommendations/new', component: RecommendationCreationPage},
  {path: 'recommendations/new/:id', component: RecommendationCreationPage},

  { path: '', redirectTo: '', pathMatch: 'full' },
  {path: '**', redirectTo: 'patient'} // TODO da nosi na 404
];
