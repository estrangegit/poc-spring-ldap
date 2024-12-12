import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError } from "rxjs";

import { Router } from "@angular/router";
import { customErrorMessages } from "@app/models/error/custom-error-messages";
import CustomException from "@app/models/error/custom-exception";
import { ErrorDetails } from "@app/models/error/error-details";
import { HTTP_ERROR_STATUS } from "@app/models/error/http-error-status";
import { TOAST_SEVERITY } from "@app/models/toast/toast-severity";
import { AuthService } from "@app/services/auth/auth.service";
import { MessageService } from "primeng/api";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

    constructor(private messageService: MessageService,
        private authService: AuthService,
        private router: Router) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            catchError((httpErrorResponse: HttpErrorResponse) => {
                if (httpErrorResponse.status && httpErrorResponse.status >= 400) {
                    const errorDetails: ErrorDetails = httpErrorResponse?.error?.customError;
                    if (errorDetails && errorDetails.errorCode) {
                        if (errorDetails.errorCode == CustomException.EXPIRED_AUTHORIZATION_EXCEPTION) {
                            this.authService.refreshToken().subscribe(() => {
                                window.location.reload();
                            })
                        } else {
                            if (errorDetails.errorCode == CustomException.REFRESH_TOKEN_EXCEPTION) {
                                this.messageService.add({
                                    severity: TOAST_SEVERITY.error,
                                    summary: customErrorMessages.get(errorDetails.errorCode)
                                });
                                this.router.navigate(['/login']);
                                this.authService.logout();

                            } else {
                                let message = customErrorMessages.get(errorDetails.errorCode);
                                if (errorDetails.errorMessage) {
                                    message = errorDetails.errorMessage
                                }
                                this.messageService.add({ severity: TOAST_SEVERITY.error, summary: message })
                            }
                        }
                    } else if (httpErrorResponse.status === HTTP_ERROR_STATUS.unauthorized.errorCode) {
                        this.messageService.add({
                            severity: TOAST_SEVERITY.error,
                            summary: HTTP_ERROR_STATUS.unauthorized.errorMessage
                        });
                    } else if (httpErrorResponse.status === HTTP_ERROR_STATUS.forbidden.errorCode) {
                        this.messageService.add({
                            severity: TOAST_SEVERITY.error,
                            summary: HTTP_ERROR_STATUS.forbidden.errorMessage
                        });
                    } else {
                        this.messageService.add({
                            severity: TOAST_SEVERITY.error,
                            summary: 'An error occurred'
                        });
                    }
                }
                throw httpErrorResponse;
            })
        );
    }
}
