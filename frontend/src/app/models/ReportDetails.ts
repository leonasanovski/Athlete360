import {AthleteReportStatus} from './types/AthleteReportStatus';

export interface ReportDetails {
  reportId: number | null;
  doctor: string;
  doctorId: number;
  patient: string;
  patientId: number;
  embg: string;
  createdAt: string;
  status: AthleteReportStatus; //GOOD IMPROVED FOLLOWUP
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
