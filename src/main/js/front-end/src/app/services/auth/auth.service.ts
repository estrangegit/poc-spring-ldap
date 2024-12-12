import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PUBLIC_AUTHENTICATION_API_URL, PUBLIC_REFRESH_TOKEN_API_URL } from '@app/models/api-url-constants';
import { Authorization } from '@app/models/auth/authorization';
import { BehaviorSubject, Observable, map } from 'rxjs';

import { Credentials } from '@app/models/auth/credentials';
import { User } from '@app/models/auth/user';
import { JwtService } from '@app/services/auth/jwt-service';

@Injectable({providedIn: 'root'})
export class AuthService {

    private _user: User = null;
    private _token: string = null;
    private _isAuth$ = new BehaviorSubject<boolean>(false);

    constructor(private http: HttpClient, private jwtService: JwtService) { }

    get user(): User {
        return this._user;
    }

    get token(): string {
        return this._token;
    }

    get isAuth$(): BehaviorSubject<boolean> {
        return this._isAuth$;
    }

    authenticate(credentials: Credentials): Observable<void> {
        const authenticateObs: Observable<Authorization> = this.http.post(PUBLIC_AUTHENTICATION_API_URL, credentials) as Observable<Authorization>;
        return authenticateObs.pipe(
            map((authorization: Authorization) => {
                this.setAuthorization(authorization);
                return;
            })
        ) ;
    }

    refreshToken(): Observable<void> {
        const refreshTokenObs: Observable<Authorization> = this.http.post(PUBLIC_REFRESH_TOKEN_API_URL, null) as Observable<Authorization>;
        return refreshTokenObs.pipe(
            map((authorization: Authorization) => {
                this.setAuthorization(authorization);
                return;
            })
        ) ;
    }
    

    setAuthorization(authorization: Authorization) {
        this._token = authorization.token;
        this._user = this.jwtService.decodeUser(authorization.token);
        this._isAuth$.next(true);
    }

    logout() {
        this._user = null;
        this._token = null;
        this._isAuth$.next(false);
    }
}
