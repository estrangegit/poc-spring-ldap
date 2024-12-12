import { APP_INITIALIZER, ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter, Router, withHashLocation } from '@angular/router';

import { HTTP_INTERCEPTORS, HttpClient, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { routes } from '@app/app.routes';
import { currentUserInitialization } from '@app/interceptors/current-user-initialization';
import { AuthService } from '@app/services/auth/auth.service';
import { Location } from '@angular/common';
import { AuthInterceptor } from '@app/interceptors/auth-interceptor';
import { ErrorInterceptor } from '@app/interceptors/error-interceptor';
import { MessageService } from 'primeng/api';

export const appConfig: ApplicationConfig = {
    providers: [
        {
            provide: APP_INITIALIZER,
            useFactory: currentUserInitialization,
            multi: true,
            deps: [Location, AuthService, HttpClient, Router]
        },
        Location,
        MessageService,
        provideZoneChangeDetection({ eventCoalescing: true }),
        provideRouter(routes, withHashLocation()),
        provideAnimations(),
        {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
        {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
        provideHttpClient(withInterceptorsFromDi()),
    ]
};
