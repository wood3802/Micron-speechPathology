import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AppRoutingModule } from './app-routing.module';
import { MainModule } from './main/main.module';
import { AuthModule } from './auth/auth.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PatientsComponent } from './main/patients/patients.component';
import { AuthHtppInterceptorService } from './main/services/auth-interceptor.service'
import { UploadPageComponent } from './main/upload-page/upload-page.component';

// ui imports
import { FormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  declarations: [
    AppComponent,
    PatientsComponent,
    DashboardComponent,
    UploadPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MainModule,
    AuthModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatSelectModule,
    MatFormFieldModule,
    MatGridListModule,
    MatButtonModule
  ],

  // for intercepting http requests to authenticate
  providers: [ { provide: HTTP_INTERCEPTORS, useClass: AuthHtppInterceptorService, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
