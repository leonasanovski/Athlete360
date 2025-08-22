import {Gender} from '../types/Gender';
import {SportsmanCategory} from '../types/SportsmanCategory';

export interface CreatePatientDTO{
  gender: Gender,
  sportsmanCategory: SportsmanCategory,
  dateOfBirth: string
}
