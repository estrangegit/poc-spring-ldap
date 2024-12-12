import { PocRole } from "@app/models/auth/poc-role";

export interface User {
    login: string,
    roles: PocRole[]
}
