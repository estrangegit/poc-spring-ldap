import { Routes } from '@angular/router';
import { AuthGuardService } from './services/auth/auth-guard.service';
import { Role1AuthGuardService } from './services/auth/role1-auth-guard.service';
import { Role2AuthGuardService } from './services/auth/role2-auth-guard.service';

export const routes: Routes = [
    {
        path: 'login', loadComponent: () => import("@app/components/login/login.component").then(m => m.LoginComponent)
    },    
    {
        path: 'home',
        canActivate: [AuthGuardService],
        loadComponent: () => import("@app/components/home/home.component").then(m => m.HomeComponent)
    },
    {
        path: 'role1',
        canActivate: [Role1AuthGuardService],
        loadComponent: () => import("@app/components/role1/role1.component").then(m => m.Role1Component)
    },
    {
        path: 'role2',
        canActivate: [Role2AuthGuardService],
        loadComponent: () => import("@app/components/role2/role2.component").then(m => m.Role2Component)
    },
    {
        path: 'vehicles',
        loadComponent: () => import("@app/components/vehicles/vehicles.component").then(m => m.VehiclesComponent)
    },
    {path: '', redirectTo: 'home', pathMatch: 'full'},
    {
      path: '**', loadComponent: () => import("@app/components/page-not-found/page-not-found.component")
        .then(m => m.PageNotFoundComponent)
    }
];
