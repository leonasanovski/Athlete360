import {Component, inject, OnInit} from '@angular/core';
import {ReportsTable} from '../../components/report-components/reports-table/reports-table';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {RecommendationCard} from '../../components/recommendation-components/recommendation-card/recommendation-card';
import {catchError, defaultIfEmpty, filter, forkJoin, map, Observable, of, switchMap, tap} from 'rxjs';
import {Recommendation} from '../../models/Recommendation';
import {RecommendationService} from '../../services/recommendation-service';
import {AsyncPipe, JsonPipe} from '@angular/common';
import {AuthService} from '../../core/services/auth-service';
import {CurrentUser} from '../../models/CurrentUser';
import {PatientService} from '../../services/patient-service';
import {Patient} from '../../models/Patient';
import {MoodService} from '../../services/mood-service';
import {NgxGaugeModule} from 'ngx-gauge';

@Component({
  selector: 'patient-page',
  imports: [
    ReportsTable,
    RouterLink,
    RecommendationCard,
    AsyncPipe,
    JsonPipe,
    NgxGaugeModule
  ],
  templateUrl: './patient-page.html',
  standalone: true,
  styleUrl: './patient-page.css'
})
export class PatientPage implements OnInit {
  authService = inject(AuthService);
  moodService = inject(MoodService)
  recommendationService = inject(RecommendationService);
  patientService = inject(PatientService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  patient: Patient | null = null;
  currentUser: CurrentUser | null = null;
  avatarName = "";
  latestRecommendations$: Observable<Recommendation[]> | undefined;
  patientId: number | null = null;

  averageScore: number | undefined;

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();

    this.latestRecommendations$ = this.authService.currentUser$.pipe(
      switchMap(user => {
        const id$: Observable<number> =
          user?.role === 'PATIENT'
            ? of(user.personId!)
            : this.route.paramMap.pipe(
              map(pm => pm.get('id')),
              filter((id): id is string => !!id),
              map(id => +id)
            );
        return id$.pipe(
          tap(id => (this.patientId = id)),
          switchMap(id =>
            this.patientService.getPatientById(id).pipe(
              tap(p => {
                this.patient = p;
                this.setAvatarName();
                if (user?.role === 'PATIENT' && this.router.url !== '/patient') {
                  this.router.navigate(['/patient']);
                }
              }),
              switchMap(() =>
                forkJoin({
                  avg: this.moodService.getMoodsForSpecificPatient(this.patientId!).pipe(
                    map(moods => {
                      if (!moods?.length) return -1;
                      const total = moods.reduce((acc, m) => acc + (m.moodDescriptionScore ?? 0), 0);
                      return Math.round((total / moods.length) * 100) / 100;
                    }),
                    catchError(() => of(null))
                  ),
                  recs: this.recommendationService.getLatestRecommendations(this.patientId!).pipe(
                    defaultIfEmpty([]),
                    catchError(() => of<Recommendation[]>([]))
                  )
                })
              ),
              tap(({avg}) => (this.averageScore = avg ?? -1)),
              map(({recs}) => recs)
            )
          )
        );
      })
    );
  }

  setAvatarName() {
    if (this.patient) {
      const name = this.patient.name.split(' ');
      this.avatarName = `${name[0][0].toUpperCase()}${name[1][0].toUpperCase()}`
    }
  }

  getMoodMeterParams(averageScore: number): { color: string, label: string } {
    if (averageScore <= 3) return {color: '#ef4444', label: 'BAD'};
    if (averageScore <= 7) return {color: '#f59e0b', label: 'STALL'};
    return {color: '#22c55e', label: 'GOOD'};
  }
}
