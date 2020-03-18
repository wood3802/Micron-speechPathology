import { Component, OnInit } from '@angular/core';
import { Patient } from '../patient';
import { PATIENTS } from '../mock-patients';

interface Recording {
  name: string;
}

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.css']
})

export class PatientsComponent implements OnInit {

  patients = PATIENTS;
  selectedPatient: Patient;
  selectedRecording: string;

  recordings: Recording[] = [
    {name: 'Recording1'},
    {name: 'Recording2'},
    {name: 'Recording3'},
  ];

  constructor() { }

  ngOnInit() {
  }

  onSelect(patient: Patient): void {
    this.selectedPatient = patient;
  }
}
