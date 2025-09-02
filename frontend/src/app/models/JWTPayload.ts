import {UserRole} from './types/UserRole';

export interface JwtPayload {
  role: UserRole;
  firstName: string;
  lastName: string;
  personId: number | null;
  userId: number;
  sub: string;//subject (embg)
  iat: number;
  exp: number;
}
