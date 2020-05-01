import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from '../main/login/login.component';
import { RegisterComponent } from '../main/register/register.component';
import { CareGiverRegisterComponent } from '../main/care-giver-register/care-giver-register.component';
import { SpeechServiceComponent } from '../main/speech-service/speech-service.component'

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    CareGiverRegisterComponent,
    SpeechServiceComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports:[LoginComponent,
    RegisterComponent,
  ]
})
export class AuthModule { }
