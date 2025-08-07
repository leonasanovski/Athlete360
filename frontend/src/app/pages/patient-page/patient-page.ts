import {Component, inject, OnInit} from '@angular/core';
import {ReportsTable} from '../../components/report/reports-table/reports-table';
import {RouterLink} from '@angular/router';
import {RecommendationCard} from '../../components/recommendation/recommendation-card/recommendation-card';
import {Observable} from 'rxjs';
import {Recommendation} from '../../models/Recommendation';
import {RecommendationService} from '../../services/recommendation-service';
import {AsyncPipe} from '@angular/common';

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
  recommendationService = inject(RecommendationService);
  latestRecommendations: Observable<Recommendation[]> | undefined;

  ngOnInit(): void {
    this.latestRecommendations = this.recommendationService.getLatestRecommendations()
  }
}
