import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { PatientsComponent } from './main/patients/patients.component';
import { AuthGuardService } from './main/services/auth-guard.service';
import {UploadPageComponent} from './main/upload-page/upload-page.component';

// guard routes with jwt authentication using canActivate:[AuthGuardService]
const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent, canActivate:[AuthGuardService]},
  { path: 'patient', component: PatientsComponent, canActivate:[AuthGuardService]},
  { path: 'upload', component: UploadPageComponent, canActivate:[AuthGuardService]}
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
