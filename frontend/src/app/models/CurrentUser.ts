import {UserRole} from './types/UserRole';

export interface CurrentUser {
  id: number | null;
  embg: string;          // from sub
  role: UserRole;
  firstName: string;
  lastName: string;
  exp: number;           // seconds since epoch
}
