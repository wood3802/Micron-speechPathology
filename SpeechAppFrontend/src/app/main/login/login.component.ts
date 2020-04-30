import { Component, OnInit, Input } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  dashboardUrl = '/dashboard';
  username = ''
  password = ''
  usertype = ''
  invalidLogin = false

  @Input() error: string | null;

  constructor(private router: Router,
    private loginservice: AuthenticationService) { }

  ngOnInit() {
  }

  // attempt to log in through the jwt authentication service and navigate to the dashboard if successful
  checkLogin(f: NgForm) {
    (this.loginservice.authenticate(f.controls['username'].value, f.controls['password'].value).subscribe(
      data => {
        this.router.navigate([this.dashboardUrl])
        this.invalidLogin = false
      },
      error => {
        this.invalidLogin = true
        this.error = error.message;
      }
    )
    );
  }
}