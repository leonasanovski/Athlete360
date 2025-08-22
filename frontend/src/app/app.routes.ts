import {Routes} from '@angular/router';
import {MoodComponent} from './components/mood/mood.component';
import {PatientPage} from './pages/patient-page/patient-page';
import {ReportDetailsPage} from './pages/report-details-page/report-details-page';
import {ReportsPage} from './pages/reports-page/reports-page';
import {MoodFormComponent} from './components/mood-form-component/mood-form-component';
import {MoodDetailsComponent} from './components/mood.details/mood.details.component';
import {DoctorPage} from './pages/doctor-page/doctor-page';
import {ReportCreationPage} from './pages/report-creation-page/report-creation-page';
import {SummaryCreationPage} from './pages/summary-creation-page/summary-creation-page';
import {PaperView} from './components/report/paper-view/paper-view';
import {LoginPageComponent} from './pages/login-page/login-page';
import {authGuard} from './core/guards/auth-guard';
import {guestGuard} from './core/guards/guest-guard';
import {PendingPage} from './pages/pending-page/pending-page';
import {DoctorFormSetup} from './components/doctor-form-setup/doctor-form-setup';
import {PatientFormSetup} from './components/patient-form-setup/patient-form-setup';

export const routes: Routes = [
  {path: 'login', component: LoginPageComponent, canActivate: [guestGuard]},
  {
    path: '',
    canActivate: [authGuard],
    canActivateChild: [authGuard],
    children: [
      {path: 'patient', component: PatientPage},
      {path: 'patient/setup', component: PatientFormSetup},
      {path: 'patient/:id', component: PatientPage},

      {path: 'doctor', component: DoctorPage},
      {path: 'doctor/setup', component: DoctorFormSetup},

      {path: 'moods', component: MoodComponent},
      {path: 'moods/add-mood', component: MoodFormComponent},
      {path: 'moods/:id/search', component: MoodComponent},
      {path: 'moods/info/:id', component: MoodDetailsComponent},

      {path: 'reports', component: ReportsPage},
      {path: 'reports/new', component: ReportCreationPage},
      {path: 'reports/new/:id', component: ReportCreationPage},
      {path: 'reports/:id', component: ReportDetailsPage},
      {path: 'reports/:id/document', component: PaperView},
      {path: 'summary/:reportId', component: SummaryCreationPage},
      {path: '', redirectTo: 'patient', pathMatch: 'full'},
      {path: 'pending', component: PendingPage},
    ]
  },
  {path: '**', redirectTo: 'patient'}
];
