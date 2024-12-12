import { HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PUBLIC_API_ROOT } from '@app/models/api-url-constants';
import { AuthService } from '@app/services/auth/auth.service';


@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private authService: AuthService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler) {
        const authToken = this.authService.token;
        let newRequest = request;
        if (authToken && !request.url.startsWith(PUBLIC_API_ROOT)) {
            newRequest = request.clone({
                headers: request.headers.set('Authorization', 'Bearer ' + authToken)
            });
        }
        return next.handle(newRequest);
    }
}
