import {Component, inject, OnInit} from '@angular/core';
import {MoodService} from '../../services/mood-service';
import {Mood} from '../../models/Mood';
import {DatePipe, SlicePipe} from '@angular/common';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {MoodStatisticsComponent} from '../../components/mood-components/mood-statistics-component/mood-statistics-component';
import {Page} from '../../models/Page';
import {filter, map, switchMap, tap} from 'rxjs';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-mood',
  imports: [
    DatePipe,
    SlicePipe,
    RouterLink,
    MoodStatisticsComponent,
    ReactiveFormsModule
  ],
  templateUrl: './mood-page.html',
  styleUrl: './mood-page.css'
})
export class MoodPage implements OnInit {
  moodEmotionOptions = ['EXCITED', 'HAPPY', 'NEUTRAL', 'TIRED', 'STRESSED', 'SAD'];
  moodProgressOptions = ['GOOD', 'BAD', 'STALL'];
  moodObjects: Mood[] = []
  page: Page<Mood> | undefined
  pageSize: number = 4 //default
  pageNumber: number = 1 //default
  moodService = inject(MoodService);
  route = inject(ActivatedRoute);
  patientId!: number | undefined
  filterForm!: FormGroup;
  fb = inject(FormBuilder)

  ngOnInit() {
    this.filterForm = this.fb.group({
      to: [''],
      from: [''],
      moodEmotion: this.fb.array([]),
      moodProgress: this.fb.array([])
    })
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

  applyFilters(): void {
    this.pageNumber = 1
    this.loadData(this.patientId!)
  }

  resetFilters(): void {
    this.filterForm.patchValue({from: '', to: ''});
    this.clearArray('moodEmotion');
    this.clearArray('moodProgress');
    this.pageNumber = 1;
    this.loadData(this.patientId!);
  }

  private clearArray(name: 'moodEmotion' | 'moodProgress') {
    const arr = this.filterForm.get(name) as FormArray;
    arr.clear();
  }

  onCheckboxChange(event: Event, name: 'moodProgress' | 'moodEmotion') {
    const checkbox = event.target as HTMLInputElement
    const controlArray: FormArray = this.filterForm.get(name) as FormArray
    if (checkbox.checked) {
      controlArray.push(this.fb.control(checkbox.value))
    } else {
      const indexUnchecked = controlArray.controls.findIndex(element => element.value === checkbox.value)
      controlArray.removeAt(indexUnchecked)
    }
  }

  loadData(patientId: number) {
    const filters = this.filterForm.value;
    this.moodService.filterSearch(
      patientId,
      filters.from,
      filters.to,
      filters.moodEmotion,
      filters.moodProgress,
      this.pageSize,
      this.pageNumber
    ).subscribe(data => {
      this.page = data;
      this.moodObjects = data.content;
    });
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
