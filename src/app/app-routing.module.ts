import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './main/login/login.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {PatientsComponent} from './patients/patients.component';


const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent},
  { path: 'patient', component: PatientsComponent}
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
