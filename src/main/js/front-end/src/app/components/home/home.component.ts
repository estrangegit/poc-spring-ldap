import { Component, inject, OnInit } from '@angular/core';
import { AuthService } from '@app/services/auth/auth.service';
import { PanelModule } from 'primeng/panel';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss'],
    imports: [
        PanelModule
    ],
    standalone: true
})
export class HomeComponent implements OnInit {
    public readonly authService = inject(AuthService);
    login: string = null;

    ngOnInit() {
        this.login = this.authService.user.login;
    }
 }
