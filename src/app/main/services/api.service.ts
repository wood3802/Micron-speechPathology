import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { User } from '../users'
import { Observable } from 'rxjs/internal/Observable';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

const API_URL = environment.apiUrl;

@Injectable({
  providedIn: 'root'
  })

export class ApiService {
  loginUrl = "http://localhost:3000/";
  currentPassword: any;
  currentUser: User;
  currentCharacter: any;
  constructor(private http: HttpClient) { }

  /*getAll() {
    return this.http.get<User[]>(API_URL +'/users');
  }*/

  login(model: any) {
    return this.http.post( this.loginUrl + 'login', model).pipe(
      map((response: any) => {
        const user = response;
        if (user.username.succeeded && user.password.succeeded){
        localStorage.setItem("user", JSON.stringify(user.userToReturn));
        localStorage.setItem("password", JSON.stringify(user.password));
        this.currentUser = user.userToReturn;
        this.currentPassword = user.passowrd;
        }
      })
    );
  }

  register(model: any) {
    return this.http.post(this.loginUrl + "register", model);
  }
  
}
