import { Component, OnInit, NgModule } from '@angular/core';
import { NgForm, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from '../../models/users'
import { Router, Routes } from '@angular/router';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {
  characters = [
    { name: "Researcher", value: "Researcher"},
    { name: "Therapist", value: "Therapist"},
    { name: "Developer", value: "Developer"}
  ];

  constructor(private router: Router, private apiService: ApiService) {
  }

  ngOnInit() {
  }

  //TODO: use model rather than raw fields
  onSubmit(f: NgForm) {
    this.apiService.register(
      f.controls['usertype'].value,
      f.controls['username'].value,
      f.controls['password'].value,
      f.controls['fname'].value,
      f.controls['lname'].value
    )
    .subscribe(
      data => {
        this.router.navigate(['/login'])
      },
      error => {
        console.error;
    });
  }
}