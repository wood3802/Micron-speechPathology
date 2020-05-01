import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-speech-service',
  templateUrl: './speech-service.component.html',
  styleUrls: ['./speech-service.component.css']
})
export class SpeechServiceComponent implements OnInit {

  API_URL = "http://localhost:3000/upload";
  uploadForm: FormGroup;
  response:any;
  filename="select file";
  flag=true;
  constructor(private formBuilder: FormBuilder, private httpClient: HttpClient) {

  }

  ngOnInit() {
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
  }

  onFileSelect(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.filename=file.name;
      this.uploadForm.get('profile').setValue(file);
    }
  }

  onSubmit() {
    this.flag=false;
    const formData = new FormData();
    formData.set('file', this.uploadForm.get('profile').value);
    console.log(formData.get('file'));
    this.httpClient.post<any>(this.API_URL,formData).subscribe(
      (res) => {this.response=res.response; this.flag=true;},
      (err) => {console.log(err); this.flag=true;}
    );
  }

}
