import {UserRole} from './types/UserRole';

export interface JwtPayload {
  role: UserRole;
  firstName: string;
  lastName: string;
  id: number | null;
  sub: string;//actually the embg
  iat: number;
  exp: number;
}
