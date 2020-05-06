import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../main/services/authentication.service'

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  isSuperUser = true;

  constructor(private logoutservice: AuthenticationService) { }

  ngOnInit(): void {
  }

  logOut() {
    this.logoutservice.logOut();
  }

}
