import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
@Component({
  selector: 'app-care-giver-register',
  templateUrl: './care-giver-register.component.html',
  styleUrls: ['./care-giver-register.component.css']
})
export class CareGiverRegisterComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  onSubmit(f: NgForm) {
    console.log(f.value);  // { first: '', last: '' }
    console.log(f.valid);  // false
  }
}
