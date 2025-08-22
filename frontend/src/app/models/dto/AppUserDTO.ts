import {UserRole} from '../types/UserRole';

export interface AppUserDTO {
  userId: number | null
  firstName: string
  lastName: string
  embg: string
  role: UserRole
  email?: string
  createdAt?: string
}
