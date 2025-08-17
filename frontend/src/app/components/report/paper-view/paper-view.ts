import {Component, ElementRef, inject, OnInit, ViewChild} from '@angular/core';
import {ReportService} from '../../../services/report-service';
import {ReportDetails} from '../../../models/ReportDetails';
import {ActivatedRoute} from '@angular/router';
import {filter, forkJoin, map, switchMap} from 'rxjs';
import {FlagLevel, ReportFlags} from '../../../models/ReportFlags';
import {METRICS_NAMES, REPORT_METRICS_RANGES, REPORT_METRICS_UNITS} from '../../../shared/constants/app-constants';
import html2canvas from 'html2canvas';
import {jsPDF} from 'jspdf';

@Component({
  selector: 'app-paper-view',
  imports: [],
  templateUrl: './paper-view.html',
  styleUrl: './paper-view.css'
})
export class PaperView implements OnInit {
  @ViewChild('exportHTML', {static: true}) exportHTML!: ElementRef;
  reportService = inject(ReportService);
  reportDetails: ReportDetails | undefined
  reportFlags: ReportFlags | undefined
  report: { label: string, value: number, level: FlagLevel | undefined }[] = []
  route = inject(ActivatedRoute)

  ngOnInit(): void {
    this.route.paramMap.pipe(
      map(link => Number(link.get('id'))),
      filter(id => !!id),
      switchMap(id =>
        forkJoin({
          reportDetails: this.reportService.getReportById(id),
          reportFlags: this.reportService.getFlagsForAttributes(id)
        })
      )
    ).subscribe(data => {
      this.reportDetails = data.reportDetails
      this.reportFlags = data.reportFlags
      this.fillReportObject()
    })
  }

  fillReportObject(): void {
    if (!this.reportDetails) return;
    this.report = [];
    for (const metric of METRICS_NAMES) {
      const flagObj = (this.reportFlags as any)?.[metric];
      if (flagObj) {
        this.report.push({
          label: metric,
          value: flagObj?.value,
          level: flagObj?.level
        });
      } else {
        const value = (this.reportDetails as any)?.[metric];
        if (value !== undefined && value !== null) {
          this.report.push({
            label: metric,
            value: value,
            level: undefined
          });
        }
      }
    }
  }

  getUnit(metric: string): string {
    return REPORT_METRICS_UNITS[metric] || '';
  }

  getReferenceRange(metric: string): string {
    return REPORT_METRICS_RANGES[metric] || '-';
  }

  flagColorClass(level?: string) {
    switch (level) {
      case 'RED':
        return 'val-red';
      case 'YELLOW':
        return 'val-yellow';
      case 'GREEN':
        return 'val-green';
      default:
        return '';
    }
  }

  exportToPdf(): void {
    const data = this.exportHTML.nativeElement;
    html2canvas(data).then(canvas => {
      const imgData = canvas.toDataURL('image/png');
      const pdf = new jsPDF('p', 'mm', 'a4');
      const imgProps = pdf.getImageProperties(imgData);
      const pdfWidth = pdf.internal.pageSize.getWidth();
      const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width - 20;
      pdf.addImage(imgData, 'PNG', 0, 0, pdfWidth, pdfHeight);
      pdf.save(`${this.reportDetails?.patient}-${this.reportDetails?.reportId}.pdf`);
    });
  }
}
