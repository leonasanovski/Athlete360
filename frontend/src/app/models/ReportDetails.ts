import {AthleteReportStatus} from './types/AthleteReportStatus';

export interface ReportDetails {
  reportId: number | null;
  doctor: string;
  patient: string;
  createdAt: string;                   // ISO date-time, e.g. "2025-08-05T14:18:10"
  status: AthleteReportStatus;
  vo2Max: number;
  restingHeartRate: number;
  underPressureHeartRate: number;
  bodyFatPercentage: number;
  leanMuscleMass: number | null;
  boneDensity: number;
  height: number;
  weight: number;
  oneRepMaxBench: number | null;
  oneRepMaxSquat: number | null;
  oneRepMaxDeadlift: number | null;
  jumpHeight: number | null;
  averageRunPerKilometer: number;
  shoulderFlexibility: number | null;
  hipFlexibility: number | null;
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
