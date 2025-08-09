import {Component, inject, Input, OnInit} from '@angular/core';
import {Observable, shareReplay} from 'rxjs';
import {ReportShort} from '../../../models/ReportShort';
import {ReportService} from '../../../services/report-service';
import {AsyncPipe, DatePipe} from '@angular/common';
import {StatusPill} from '../status-pill/status-pill';
import {RouterLink} from '@angular/router';
import {Patient} from '../../../models/Patient';
import {PageResponse} from '../../../models/PageResponse';

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
export class ReportsTable implements OnInit {
  reportService = inject(ReportService);

  reports: ReportShort[] = [];
  totalElements = 0;
  page = 0;
  size = 10;

  sortField: string = 'createdAt';
  sortDirection: 'asc' | 'desc' = 'desc';

  ngOnInit() {
    this.loadReports();
  }

  loadReports(page: number = this.page) {
      const sortParam = `${this.sortField},${this.sortDirection}`;
      this.reportService.getReportsShort(page, this.size, sortParam)
        .subscribe((res: PageResponse<ReportShort>) => {
          this.reports = res.content;
          this.totalElements = res.totalElements;
          this.page = res.number;
        })
  }

  onPageChange(newPage: number) {
    this.loadReports(newPage);
  }

  toggleSortByCreatedAt() {
    if (this.sortField === 'createdAt') {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = 'createdAt';
      this.sortDirection = 'asc';
    }
    this.loadReports(0);
  }
}
