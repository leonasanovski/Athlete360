import {Gender} from "./types/Gender";
import {SportsmanCategory} from "./types/SportsmanCategory";

export interface Patient {
  patientId: number,
  name: string,
  embg: string,
  doctor: string,
  gender: Gender,
  sportsmanCategory: SportsmanCategory,
  email: string,
  dateOfBirth: string,
  dateOfLatestCheckup: string,
}
