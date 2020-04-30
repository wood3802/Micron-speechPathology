import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { User } from '../../models/users'
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

const API_URL = environment.apiUrl;

@Injectable({
  providedIn: 'root'
  })

export class ApiService {
  loginUrl = "http://localhost:8080/";
  currentPassword: any;
  currentUser: User;
  currentCharacter: any;
  constructor(private http: HttpClient) { }

  register(model: any) {
    return this.http.post(this.loginUrl + "register", model);
  }
  
}
