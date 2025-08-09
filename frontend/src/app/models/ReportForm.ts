import {AthleteReportStatus} from './types/AthleteReportStatus';

export interface ReportForm {
  doctorId: number;
  embg: string;
  status: AthleteReportStatus;
  vo2Max: number;
  restingHeartRate: number;
  underPressureHeartRate: number;
  bodyFatPercentage: number;
  leanMuscleMass?: number;
  boneDensity: number;
  height: number;
  weight: number;
  oneRepMaxBench?: number;
  oneRepMaxSquat?: number;
  oneRepMaxDeadlift?: number;
  jumpHeight?: number;
  averageRunPerKilometer: number;
  shoulderFlexibility?: number;
  hipFlexibility?: number;
  balanceTime: number;
  reactionTime: number;
  coreStabilityScore: number;
  hemoglobin: number;
  glucose: number;
  creatinine: number;
  vitaminD: number;
  iron: number;
  testosterone: number;
  cortisol: number;
}
