import {Component, inject, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {ReportShort} from '../../../models/ReportShort';
import {ReportService} from '../../../services/report-service';
import {AsyncPipe, DatePipe} from '@angular/common';
import {StatusPill} from '../status-pill/status-pill';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'reports-table',
  imports: [
    AsyncPipe,
    StatusPill,
    DatePipe,
    RouterLink
  ],
  templateUrl: './reports-table.html',
  standalone: true,
  styleUrl: './reports-table.css'
})
export class ReportsTable implements OnInit{
  reportService = inject(ReportService);
  reports$: Observable<ReportShort[]> | undefined;

  dummyId = 1;

  ngOnInit(): void {
    this.reports$ = this.reportService.getReportsShortByPatientId(this.dummyId);
  }

}
