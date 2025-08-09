import {UserRole} from "./types/UserRole";

export interface CurrentUser {
    id: number;
    role: UserRole;
}
