import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MenuItem, PrimeIcons, PrimeTemplate } from 'primeng/api';
import { MenubarModule } from 'primeng/menubar';
import {Button} from 'primeng/button';
import { Subscription } from 'rxjs';
import { AuthService } from '@app/services/auth/auth.service';
import { NgIf } from '@angular/common';
import { PocRole } from './models/auth/poc-role';
import { ToastModule } from 'primeng/toast';
import { SpinnerComponent } from '@app/components/spinner/spinner.component';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [
        SpinnerComponent,
        ToastModule,
        RouterOutlet,
        MenubarModule,
        PrimeTemplate,
        Button,
        NgIf
    ],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit, OnDestroy {
    public readonly authService = inject(AuthService);
    private readonly router = inject(Router);

    isAuthSub: Subscription;
    items: MenuItem[] | undefined;
    showMenuBar: boolean;

    homeMenuItem: MenuItem = {
        label: 'home',
        icon: PrimeIcons.HOME,
        routerLink: ['/home'],
        visible: true
    };
    role1MenuItem: MenuItem = {
        label: 'role1',
        icon: PrimeIcons.CARET_UP,
        routerLink: ['/role1'],
        visible: true
    };
    role2MenuItem: MenuItem = {
        label: 'role2',
        icon: PrimeIcons.CHEVRON_CIRCLE_UP,
        routerLink: ['/role2'],
        visible: true
    };
    vehicleMenuItem: MenuItem = {
        label: 'vehicle',
        icon: PrimeIcons.CAR,
        routerLink: ['/vehicles'],
        visible: true
    };

    ngOnInit() {
        this.isAuthSub = this.authService.isAuth$.subscribe(
            (isAuth: boolean) => {
              this.showMenuBar = isAuth;
              this.homeMenuItem.visible = isAuth;
              this.role1MenuItem.visible = isAuth && this.authService.user.roles.indexOf(PocRole.ROLE1) >= 0;
              this.role2MenuItem.visible = isAuth && this.authService.user.roles.indexOf(PocRole.ROLE2) >= 0;
              this.vehicleMenuItem.visible = isAuth && this.authService.user.roles.indexOf(PocRole.ROLE1) >= 0;
            })        
        this.items = [this.homeMenuItem, this.role1MenuItem, this.role2MenuItem, this.vehicleMenuItem];
    }

    ngOnDestroy() {
        this.isAuthSub.unsubscribe();
      }

    logout() {
        this.authService.logout();
        this.router.navigate(['/login']).then();
      }
}
