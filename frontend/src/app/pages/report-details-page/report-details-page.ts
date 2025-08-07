import {Component, inject, OnInit} from '@angular/core';
import {filter, map, switchMap} from 'rxjs';
import {ReportDetails} from '../../models/ReportDetails';
import {ReportService} from '../../services/report-service';
import {ActivatedRoute} from '@angular/router';
import {DatePipe} from '@angular/common';
import {StatusPill} from '../../components/report/status-pill/status-pill';
import {Recommendation} from '../../models/Recommendation';
import {RecommendationService} from '../../services/recommendation-service';
import {RecommendationCard} from '../../components/recommendation/recommendation-card/recommendation-card';

@Component({
  selector: 'report-details-page',
  imports: [
    DatePipe,
    StatusPill,
    RecommendationCard
  ],
  templateUrl: './report-details-page.html',
  standalone: true,
  styleUrl: './report-details-page.css'
})
export class ReportDetailsPage implements OnInit{
  reportService = inject(ReportService);
  recommendationService = inject(RecommendationService);
  route = inject(ActivatedRoute);
  report: ReportDetails | undefined;
  recommendations: Recommendation[] = [];

  ngOnInit(): void {
    this.route.paramMap.pipe(
      map(pm => pm.get('id')),
      filter(id => id != null),
      switchMap(id => this.reportService.getReportById(+id).pipe(
        switchMap(report => {
          this.report = report;
          return this.recommendationService.getRecommendationsByReportId(report.reportId!);
        })
      ))
    ).subscribe(recommendations => this.recommendations = recommendations);
  }

}
