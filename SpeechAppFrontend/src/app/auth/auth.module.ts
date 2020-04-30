import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from '../main/login/login.component';
import { RegisterComponent } from '../main/register/register.component';
import { CareGiverRegisterComponent } from '../main/care-giver-register/care-giver-register.component';


@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    CareGiverRegisterComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    FormsModule
  ],
  exports:[LoginComponent,
    RegisterComponent,
  ]
})
export class AuthModule { }
