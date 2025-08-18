import {Component, inject, OnInit} from '@angular/core';
import {filter, forkJoin, map, switchMap} from 'rxjs';
import {ReportDetails} from '../../models/ReportDetails';
import {ReportService} from '../../services/report-service';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {DatePipe} from '@angular/common';
import {StatusPill} from '../../components/report/status-pill/status-pill';
import {Recommendation} from '../../models/Recommendation';
import {RecommendationService} from '../../services/recommendation-service';
import {RecommendationCard} from '../../components/recommendation/recommendation-card/recommendation-card';
import {SummaryService} from '../../services/summary-service';
import {Summary} from '../../models/Summary';
import {AuthService} from '../../services/auth-service';

@Component({
  selector: 'report-details-page',
  imports: [
    DatePipe,
    StatusPill,
    RecommendationCard,
    RouterLink
  ],
  templateUrl: './report-details-page.html',
  standalone: true,
  styleUrl: './report-details-page.css'
})
export class ReportDetailsPage implements OnInit{
  authService = inject(AuthService);
  reportService = inject(ReportService);
  recommendationService = inject(RecommendationService);
  summaryService = inject(SummaryService);

  route = inject(ActivatedRoute);
  router = inject(Router);

  report: ReportDetails | undefined;
  recommendations: Recommendation[] = [];
  summary: Summary | undefined;
  currentUserId?: number;

  ngOnInit(): void {
    this.authService.currentUser$
      .subscribe(user => {
        this.currentUserId = user.id
      })

    this.route.paramMap.pipe(
      map(pm => pm.get('id')),
      filter(id => id != null),
      switchMap(id => this.reportService.getReportById(+id).pipe(
        switchMap(report => {
          this.report = report;
          return forkJoin({
            recommendations: this.recommendationService.getRecommendationsByReportId(report.reportId!),
            summary: this.summaryService.getSummaryByReportId(report.reportId!)
          })
        })
      ))
    ).subscribe(({recommendations, summary}) => {
      this.recommendations = recommendations;
      this.summary = summary ?? undefined;
    });
  }

goToNewRecommendation(reportId?: number | null) {
    if (!reportId) return;
    this.router.navigate(['/recommendations/new'], { queryParams: { reportId } });
  }
}
