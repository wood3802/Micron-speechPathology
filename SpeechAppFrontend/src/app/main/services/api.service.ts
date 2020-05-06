import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { User } from '../../models/users'
import { map } from 'rxjs/operators';
import { Patient } from '../../models/patients'
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl;

@Injectable({
  providedIn: 'root'
  })

export class ApiService {
  constructor(private http: HttpClient) {}

  // posts user information for regular users
  //TODO: pass as a model rather than raw fields
  register(usertype, username, password, fname, lname) {
    return this.http.post<any>(""+API_URL+"/register", {usertype, username, password, fname, lname})
    .pipe(
      map(Data => {
        return Data;
      })
    );
  }

  // posts patient information from the caregiver's page
  patientRegister(model: Patient) {
    return this.http.post(""+API_URL+"/patientregister", model);
  }
  
}
