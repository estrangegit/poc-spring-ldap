import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { PocRole } from '@app/models/auth/poc-role';
import { Observable, map } from 'rxjs';
import { AuthService } from '@app/services/auth/auth.service';

@Injectable({ providedIn: 'root' })
export class Role2AuthGuardService {
    private readonly authService = inject(AuthService);
    private readonly router = inject(Router)

    canActivate(): Observable<boolean> {
        return this.authService.isAuth$.pipe(
            map((isAuth: boolean) => {
                if (!isAuth || this.authService.user.roles.indexOf(PocRole.ROLE2) < 0) {
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
