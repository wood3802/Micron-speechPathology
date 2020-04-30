import { Component, OnInit, NgModule } from '@angular/core';
import { NgForm, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, Routes } from '@angular/router';
import { first } from 'rxjs/operators';
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

  constructor(private apiService: ApiService ) { }

  ngOnInit() {
  }

  onSubmit(f: NgForm) {
    const registerObserver = {
      next: x => {
        console.log('User register: ');
        console.log(f.value);
      },
      error: err => console.log('got an error: ' + err)
    };
    this.apiService.register(f.value).subscribe(registerObserver);
  }
}