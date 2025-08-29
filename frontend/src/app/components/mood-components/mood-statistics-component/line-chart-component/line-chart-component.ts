import {Component, Input, OnChanges} from '@angular/core';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {MoodStatisticsDTO} from '../../../../models/MoodStatistics';

@Component({
  selector: 'app-line-chart-component',
  standalone: true,
  imports: [NgxChartsModule],
  template: `
    <ngx-charts-line-chart
      [results]="chartData"
      [view]="view"
      [xAxis]="showXAxis"
      [yAxis]="showYAxis"
      [showXAxisLabel]="showXAxisLabel"
      [showYAxisLabel]="showYAxisLabel"
      [xAxisLabel]="xAxisLabel"
      [yAxisLabel]="yAxisLabel"
      [timeline]="timeline"
      [yScaleMax]="yScaleMax"
      [yScaleMin]="yScaleMin"
      [yAxisTickFormatting]="yAxisTickFormatting"
    >
    </ngx-charts-line-chart>
  `,
  styleUrl: './line-chart-component.css'
})
export class LineChartComponent implements OnChanges {
  @Input() moodStatistics: MoodStatisticsDTO | null = null;
  chartData: any[] = [];
  view: [number, number] = [400, 350];
  showXAxis = true;
  showYAxis = true;
  showXAxisLabel = true;
  xAxisLabel = 'Date Range Selector';
  showYAxisLabel = true;
  yAxisLabel = 'Mood Progress';
  timeline = true;
  yScaleMin = -0.5;
  yScaleMax = 2.5;
  private numberToLabel: { [k: number]: string } = {0: 'Bad', 1: 'Stall', 2: 'Good'};
  private moodToNumber: Record<string, number> = {
    "BAD": 0,
    "STALL": 1,
    "GOOD": 2
  }

  ngOnChanges() {
    this.chartData = this.moodStatistics?.progressOverTime ? [{
      name: 'Progress',
      series: [...this.moodStatistics.progressOverTime] // take every element from it and put it into array
        .sort((time_a, time_b) => new Date(time_a.date).getTime() - new Date(time_b.date).getTime())
        .map(data => ({
          name: new Date(data.date),
          value: this.moodToNumber[data.progress]
        }))
    }] : [];
  }

  yAxisTickFormatting = (v: number) => this.numberToLabel[v] ?? v.toString();
}
