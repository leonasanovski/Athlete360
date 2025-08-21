import {UserRole} from './types/UserRole';

export interface CurrentUser {
  personId: number | null;
  userId: number;
  embg: string;          // from sub
  role: UserRole;
  firstName: string;
  lastName: string;
  exp: number;           // seconds since epoch
}
