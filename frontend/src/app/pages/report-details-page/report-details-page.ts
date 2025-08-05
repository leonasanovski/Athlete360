import { Component } from '@angular/core';
import {ReportsTable} from '../../components/report/reports-table/reports-table';

@Component({
  selector: 'report-details-page',
  imports: [
    ReportsTable
  ],
  templateUrl: './report-details-page.html',
  styleUrl: './report-details-page.css'
})
export class ReportDetailsPage {

}
