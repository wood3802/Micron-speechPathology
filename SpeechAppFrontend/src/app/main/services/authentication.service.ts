import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl;

@Injectable({
  providedIn: 'root'
})

export class AuthenticationService {
  constructor(private httpClient: HttpClient) {}
  
  // post username and password to backend and store a jwt token if authenticated
  authenticate(username, password) {
    return this.httpClient
      .post<any>(""+API_URL+"/authenticate", { username, password })
      .pipe(
        map(userData => {
          //TODO: Consider not using session storage for this information as it can be edited by the client
          sessionStorage.setItem("username", username);
          sessionStorage.setItem("usertype", userData.usertype);
          let tokenStr = "Bearer " + userData.token;
          sessionStorage.setItem("token", tokenStr);
          return userData;
        })
      );
  }

  // checks for the stored username from a successful login
  isUserLoggedIn() {
    let user = sessionStorage.getItem("username");
    console.log(!(user === null));
    return !(user === null);
  }

  // removes username field to invalidate the token
  logOut() {
    sessionStorage.removeItem("username");
  }
}