import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Credentials } from '@app/models/auth/credentials';
import { PanelModule } from "primeng/panel";
import { NgIf } from "@angular/common";
import { Button } from "primeng/button";
import { InputTextModule } from "primeng/inputtext";
import { AuthService } from '@app/services/auth/auth.service';
import { Router } from '@angular/router';


@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
    imports: [
        PanelModule,
        ReactiveFormsModule,
        NgIf,
        Button,
        InputTextModule
    ],
    standalone: true
})
export class LoginComponent implements OnInit {
    private readonly authService = inject(AuthService);
    private readonly router = inject(Router)
    loginForm: FormGroup;
    credentials: Credentials = { login: null, password: null };

    constructor() {
    }

    ngOnInit() {
        this.loginForm = new FormGroup({
            login: new FormControl(this.credentials.login, [Validators.required]),
            password: new FormControl(this.credentials.password, [Validators.required])
        })
    }

    get login() {
        return this.loginForm.get('login');
    }

    get password() {
        return this.loginForm.get('password');
    }

    onLogin() {
        const login = this.loginForm.get('login').value;
        const password = this.loginForm.get('password').value;
        const credentials: Credentials = { login, password };
        this.authService.authenticate(credentials).subscribe({
            next: this.handleAuthenticateResponse.bind(this),
            error: this.handleAuthenticateError.bind(this)
          })
    }

    handleAuthenticateResponse(): void {
        this.router.navigate(['/home']);
      }
    
      handleAuthenticateError(): void {
        this.authService.isAuth$.next(false);
        this.loginForm.reset({});
      }
}
