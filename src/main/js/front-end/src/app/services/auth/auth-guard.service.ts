import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, map } from 'rxjs';
import { AuthService } from '@app/services/auth/auth.service';

@Injectable({ providedIn: 'root' })
export class AuthGuardService {
    private readonly authService = inject(AuthService);
    private readonly router = inject(Router)


    canActivate(): Observable<boolean> {
        return this.authService.isAuth$.pipe(
            map((isAuth: boolean) => {
                if (!isAuth) {                    
                    this.router.navigate(['/login']);
                    this.authService.logout();
                    return false;
                } else {
                    return true;
                }
            })
        )
    }
}
