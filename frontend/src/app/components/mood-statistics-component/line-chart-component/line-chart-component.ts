import {Component, Input, OnChanges} from '@angular/core';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {MoodStatisticsDTO} from '../../../models/MoodStatistics';

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
      [yAxisTickFormatting]="yAxisTickFormatting">
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
  private moodToNumber: Record<'BAD' | 'STALL' | 'GOOD', 0 | 1 | 2> = {
    BAD: 0, STALL: 1, GOOD: 2
  };
  private numberToLabel: { [k: number]: string } = {0: 'Bad', 1: 'Stall', 2: 'Good'};
  //todo to make the progress as a name, not a number, a little refactor to do
  ngOnChanges() {
    this.chartData = this.moodStatistics?.progressOverTime ? [{
      name: 'Progress',
      series: [...this.moodStatistics.progressOverTime]
        .sort((a, b) => new Date(a.date).getTime() - new Date(b.date).getTime())
        .map(entry => ({
          name: new Date(entry.date),
          value: this.moodToNumber[entry.progress]
        }))
    }] : [];
  }

  yAxisTickFormatting = (v: number) => this.numberToLabel[v] ?? v.toString();
}
