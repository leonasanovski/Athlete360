import {Component, inject, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {ReportShort} from '../../../models/ReportShort';
import {ReportService} from '../../../services/report-service';
import {AsyncPipe, JsonPipe} from '@angular/common';

@Component({
  selector: 'reports-table',
  imports: [
    AsyncPipe,
    JsonPipe
  ],
  templateUrl: './reports-table.html',
  styleUrl: './reports-table.css'
})
export class ReportsTable implements OnInit{
  reportService = inject(ReportService);
  reports$: Observable<ReportShort[]> | undefined;

  ngOnInit(): void {
    this.reports$ = this.reportService.getReportsShortByPatientId(1);
  }

}
