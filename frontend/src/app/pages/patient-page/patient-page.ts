import {Component, inject, OnInit} from '@angular/core';
import {ReportsTable} from '../../components/report/reports-table/reports-table';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {RecommendationCard} from '../../components/recommendation/recommendation-card/recommendation-card';
import {filter, map, Observable, switchMap, tap} from 'rxjs';
import {Recommendation} from '../../models/Recommendation';
import {RecommendationService} from '../../services/recommendation-service';
import {AsyncPipe} from '@angular/common';
import {AuthService} from '../../services/auth-service';

@Component({
  selector: 'patient-page',
  imports: [
    ReportsTable,
    RouterLink,
    RecommendationCard,
    AsyncPipe
  ],
  templateUrl: './patient-page.html',
  standalone: true,
  styleUrl: './patient-page.css'
})
export class PatientPage implements OnInit{
  authService = inject(AuthService);
  recommendationService = inject(RecommendationService);
  route = inject(ActivatedRoute);
  router = inject(Router);

  latestRecommendations$: Observable<Recommendation[]> | undefined;
  patientId: number | null = null;

  ngOnInit(): void {
    this.latestRecommendations$ = this.authService.currentUser$.pipe(
      switchMap(user => {
        if (user?.role === 'PATIENT') {
          this.patientId = user.id;
          this.router.navigate(['/patient']);
          return this.recommendationService.getLatestRecommendations(this.patientId!);
        }

        return this.route.paramMap.pipe(
          map(params => params.get('id')),
          filter((id): id is string => !!id),
          tap(id => (this.patientId = +id)),
          switchMap(id => this.recommendationService.getLatestRecommendations(+id))
        );
      })
    );
  }
}
