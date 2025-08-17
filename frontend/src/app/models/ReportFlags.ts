export type FlagLevel = 'GREEN'|'YELLOW'|'RED';

export interface MetricFlagDTO<T> {
  value: T;
  level: FlagLevel;
}

export interface ReportFlags {
  vo2Max?: MetricFlagDTO<number>;
  restingHeartRate?: MetricFlagDTO<number>;
  underPressureHeartRate?: MetricFlagDTO<number>;
  bodyFatPercentage?: MetricFlagDTO<number>;
  leanMuscleMass?: MetricFlagDTO<number>;
  boneDensity?: MetricFlagDTO<number>;
  bmi?: MetricFlagDTO<number>;
  hemoglobin?: MetricFlagDTO<number>;
  glucose?: MetricFlagDTO<number>;
  vitaminD?: MetricFlagDTO<number>;
  iron?: MetricFlagDTO<number>;
  testosterone?: MetricFlagDTO<number>;
  cortisol?: MetricFlagDTO<number>;
}
