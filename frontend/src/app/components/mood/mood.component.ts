import {Component, inject, OnInit} from '@angular/core';
import {MoodService} from '../../services/mood-service';
import {Mood} from '../../models/Mood';
import {DatePipe, SlicePipe} from '@angular/common';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {MoodStatisticsComponent} from '../mood-statistics-component/mood-statistics-component';
import {Page} from '../../models/Page';
import {PatientService} from '../../services/patient-service';
import {filter, map, switchMap, tap} from 'rxjs';

@Component({
  selector: 'app-mood',
  imports: [
    DatePipe,
    SlicePipe,
    RouterLink,
    MoodStatisticsComponent
  ],
  templateUrl: './mood.component.html',
  styleUrl: './mood.component.css'
})
export class MoodComponent implements OnInit {
  moodObjects: Mood[] = []
  page: Page<Mood> | undefined
  pageSize: number = 4 //default
  pageNumber: number = 1 //default
  moodService = inject(MoodService);
  route = inject(ActivatedRoute);
  patientId!: number | undefined

  ngOnInit() {
    this.route.paramMap.pipe(
      map(obj => Number(obj.get('id'))),
      filter(id => !!id),
      map(id => id!),
      tap(id => this.patientId = id),
      switchMap(patientId => this.moodService
        .filterSearch(patientId,
          undefined,
          undefined,
          undefined,
          undefined,
          this.pageSize,
          this.pageNumber))
    ).subscribe(data => {
      this.moodObjects = data.content
      this.page = data
    })
  }

  loadData(patientId: number) {
    this.moodService.filterSearch(
      patientId,
      undefined,
      undefined,
      undefined,
      undefined,
      this.pageSize,
      this.pageNumber).subscribe(
      data => {
        this.page = data
        this.moodObjects = data.content
      }
    )
  }

  getPageNumbers(): number[] {
    if (!this.page) return []
    return Array.from({length: this.page.totalPages}, (_, i) => i + 1);
  }

  goToPage(pageNum: number): void {
    if (!this.page) return;
    if (pageNum < 1 || pageNum > this.page.totalPages) return;
    this.pageNumber = pageNum.valueOf()
    this.loadData(this.patientId!)
  }

  prev(): void {
    if (this.page?.first) return;
    this.pageNumber -= 1
    this.loadData(this.patientId!)
  }

  next(): void {
    if (this.page?.last) return;
    this.pageNumber += 1
    this.loadData(this.patientId!)
  }
}
