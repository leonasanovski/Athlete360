import {Gender} from "./types/Gender";
import {SportsmanCategory} from "./types/SportsmanCategory";

export interface Patient {
    patientId: number,
    firstName: string,
    lastName: string,
    doctorInfo: string,
    gender: Gender,
    sportCategory: SportsmanCategory,
    email: string,
    dateOfBirth: string
}
