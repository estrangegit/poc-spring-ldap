import { Injectable } from '@angular/core';
import { PocRole } from '@app/models/auth/poc-role';
import { User } from '@app/models/auth/user';
import * as jwt_decode from "jwt-decode";

@Injectable({providedIn: 'root'})
export class JwtService {

    constructor() { }

    decodeUser(token: string): User {
        const decodedUser: any = jwt_decode.jwtDecode(token);

        let user: User = {login: null, roles: null };
        user.login = decodedUser.login;

        const roles: string = decodedUser.roles;
        user.roles = roles.split(', ').map(role => PocRole[role as keyof typeof PocRole])

        return user;
    }
}
