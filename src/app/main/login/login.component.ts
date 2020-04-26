import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ApiService } from '../services/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  dashboardUrl = "/dashboard";
  constructor(
    private apiService: ApiService,
    private router: Router
    ) { }

  ngOnInit() {
  }

  onSubmit(f: NgForm) {
    const loginObserver = {
      next: x => {
        console.log('User logged in: ');
        this.router.navigate([this.dashboardUrl])
      },
      error: err => console.log('got an error: ' + err)
    };
    this.apiService.login(f.value).subscribe(loginObserver);
    //console.log(f.value);  // { first: '', last: '' }
    //console.log(f.valid);  // false
  }
}
