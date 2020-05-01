import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from '../main/login/login.component';
import { RegisterComponent } from '../main/register/register.component';
import { CareGiverRegisterComponent } from '../main/care-giver-register/care-giver-register.component';
import { SpeechServiceComponent } from '../main/speech-service/speech-service.component'

const routes: Routes = [
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'care-giver-register', component: CareGiverRegisterComponent},
  { path: 'speech-service', component: SpeechServiceComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
