import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import {MapComponent} from './features/map/map.component';
import {JobsPageComponent} from './features/jobs/jobs-page/jobs-page.component';
import { AdminLoginComponent } from './features/admin/admin-login.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'admin/login', component: AdminLoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'map', component: MapComponent },
  { path: 'jobs', component: JobsPageComponent },
  { path: '', redirectTo: 'map', pathMatch: 'full' }
];
