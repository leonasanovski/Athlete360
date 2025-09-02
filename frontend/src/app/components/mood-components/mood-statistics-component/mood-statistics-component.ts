import {Component, inject, OnInit} from '@angular/core';
import {Patient} from '../../../models/Patient';
import {PatientService} from '../../../services/patient-service';
import {MoodService} from '../../../services/mood-service';
import {ActivatedRoute} from '@angular/router';
import {DatePipe} from '@angular/common';
import {MoodStatisticsDTO} from '../../../models/MoodStatistics';
import {filter, map, switchMap, tap} from 'rxjs';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {LineChartComponent} from './line-chart-component/line-chart-component';

@Component({
  selector: 'app-mood-statistics-component',
  imports: [
    DatePipe,
    NgxChartsModule,
    LineChartComponent
  ],
  templateUrl: './mood-statistics-component.html',
  styleUrl: './mood-statistics-component.css'
})
export class MoodStatisticsComponent implements OnInit {
  patientService: PatientService = inject(PatientService)
  moodService: MoodService = inject(MoodService)
  moodStatistics: MoodStatisticsDTO | undefined
  route = inject(ActivatedRoute)
  patient: Patient | undefined
  pieChartData: { name: string, value: number }[] = []
  advancedPieChartData: { name: string, value: number }[] = []
  colorSchema: any = {domain: ['#6A994E', '#F2E8CF', '#BC4749', '#386641', '#A7C957']}
  moodStatisticsSelectedView: 'pie' | 'table' = 'pie';

  ngOnInit(): void {
    this.route.paramMap.pipe(
      map(mapping => mapping.get('id')),
      filter(id => !!id),
      switchMap(patientId => this.patientService.getPatientById(+patientId!).pipe(
        tap(patient => this.patient = patient),
        switchMap(patient => this.moodService.getMoodStatisticsForPatient(patient.patientId)),
      ))
    ).subscribe(data => {
      this.moodStatistics = data
      this.pieChartData = [
        {
          name: 'Good',
          value: this.moodStatistics?.moodProgressCounts['GOOD'] ?? 0
        },
        {
          name: 'Stall',
          value: this.moodStatistics?.moodProgressCounts['STALL'] ?? 0
        },
        {
          name: 'Bad',
          value: this.moodStatistics?.moodProgressCounts['BAD'] ?? 0
        }
      ]
      this.advancedPieChartData = [
        {
          name: 'Happy',
          value: this.moodStatistics?.moodEmotionCounts['HAPPY'] ?? 0
        },
        {
          name: 'Excited',
          value: this.moodStatistics?.moodEmotionCounts['EXCITED'] ?? 0
        },
        {
          name: 'Neutral',
          value: this.moodStatistics?.moodEmotionCounts['NEUTRAL'] ?? 0
        },
        {
          name: 'Sad',
          value: this.moodStatistics?.moodEmotionCounts['SAD'] ?? 0
        },
        {
          name: 'Stressed',
          value: this.moodStatistics?.moodEmotionCounts['STRESSED'] ?? 0
        }
      ]
    })
  }
}
