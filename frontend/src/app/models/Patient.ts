import {Gender} from "./types/Gender";
import {SportsmanCategory} from "./types/SportsmanCategory";

export interface Patient {
  patientId: number,
  name: string,
  embg: string,
  doctorInfo: string,
  gender: Gender,
  sportCategory: SportsmanCategory,
  email: string,
  dateOfBirth: string,
  dateOfLatestCheckup: string,
}
