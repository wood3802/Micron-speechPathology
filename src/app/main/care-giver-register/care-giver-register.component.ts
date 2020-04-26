import { Component, OnInit, NgModule } from '@angular/core';
import { NgForm, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, Routes } from '@angular/router';
import { first } from 'rxjs/operators';
import { ApiService } from '../services/api.service';
@Component({
  selector: 'app-care-giver-register',
  templateUrl: './care-giver-register.component.html',
  styleUrls: ['./care-giver-register.component.css']
})
export class CareGiverRegisterComponent implements OnInit {
  
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
    //console.log(f.value);  // { first: '', last: '' }
    //console.log(f.valid);  // false
  }
}
