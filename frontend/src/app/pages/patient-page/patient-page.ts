import {Component, inject, OnInit} from '@angular/core';
import {ReportsTable} from '../../components/report/reports-table/reports-table';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {RecommendationCard} from '../../components/recommendation/recommendation-card/recommendation-card';
import {catchError, defaultIfEmpty, filter, forkJoin, map, Observable, of, switchMap, tap} from 'rxjs';
import {Recommendation} from '../../models/Recommendation';
import {RecommendationService} from '../../services/recommendation-service';
import {AsyncPipe, JsonPipe} from '@angular/common';
import {AuthService} from '../../services/auth-service';
import {CurrentUser} from '../../models/CurrentUser';
import {PatientService} from '../../services/patient-service';
import {Patient} from '../../models/Patient';
import {logger} from 'html2canvas/dist/types/core/__mocks__/logger';

@Component({
  selector: 'patient-page',
  imports: [
    ReportsTable,
    RouterLink,
    RecommendationCard,
    AsyncPipe,
    JsonPipe
  ],
  templateUrl: './patient-page.html',
  standalone: true,
  styleUrl: './patient-page.css'
})
export class PatientPage implements OnInit{
  authService = inject(AuthService);
  recommendationService = inject(RecommendationService);
  patientService = inject(PatientService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  patient: Patient | null = null;
  currentUser: CurrentUser | null = null;
  avatarName = "";
  latestRecommendations$: Observable<Recommendation[]> | undefined;
  patientId: number | null = null;

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();

    this.latestRecommendations$ = this.authService.currentUser$.pipe(
      switchMap(user => {
        let id$: Observable<number>;

        if (user?.role === 'PATIENT') {
          this.patientId = user.personId;
          id$ = of(this.patientId!);
          this.router.navigate(['/patient']);
        } else {
          id$ = this.route.paramMap.pipe(
            map(params => params.get('id')),
            filter((id): id is string => !!id),
            map(id => +id),
            tap(id => (this.patientId = id))
          );
        }

        // fetch patient first, then recommendations
        return id$.pipe(
          switchMap(id =>
            this.patientService.getPatientById(id).pipe(
              tap(patient => {
                this.patient = patient;
                this.setAvatarName();
              }),
              switchMap(() =>
                this.recommendationService.getLatestRecommendations(id).pipe(
                  defaultIfEmpty([]), // in case no recommendations
                  catchError(err => {
                    console.warn('Failed to fetch recommendations', err);
                    return of([]); // always return empty array on error
                  })
                )
              )
            )
          )
        );
      })
    );
  }

  setAvatarName() {
    if(this.patient) {
      const name = this.patient.name.split(' ');
      this.avatarName = `${name[0][0].toUpperCase()}${name[1][0].toUpperCase()}`
    }
  }
}
