import {Component} from '@angular/core';
import {ReportsTable} from '../../components/report-components/reports-table/reports-table';

@Component({
  selector: 'app-reports-page',
  imports: [
    ReportsTable
  ],
  templateUrl: './reports-page.html',
  standalone: true,
  styleUrl: './reports-page.css'
})
export class ReportsPage {
}
