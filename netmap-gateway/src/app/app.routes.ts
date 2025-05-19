import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import {MapComponent} from './features/map/map.component';
import {JobsPageComponent} from './features/jobs/jobs-page/jobs-page.component';
import { AdminLoginComponent } from './features/admin/admin-login.component';
import {JobPostComponent} from './features/jobs/job-post/job-post.component';
import {AdminJobApprovalsComponent} from './features/admin/admin-job-approvals.component';
import {ProfileEditComponent} from './features/profile/profile-edit/profile-edit.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'admin/login', component: AdminLoginComponent },
  { path: 'admin/job-approvals', component: AdminJobApprovalsComponent },
  { path: 'map', component: MapComponent },
  { path: 'jobs', component: JobsPageComponent },
  { path: 'job-post', component: JobPostComponent },
  { path: 'profile/edit', component: ProfileEditComponent },
  { path: '', redirectTo: 'map', pathMatch: 'full' }
];
