import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CloudUploadService {
  constructor(private http: HttpClient) { }
  sendPostRequest(BUCKET_NAME, OBJECT_NAME) {
    let url;
    url = 'https://storage.googleapis.com/upload/storage/v1/b/${BUCKET_NAME}/o?uploadType=media&name=${OBJECT_NAME}';
    const headers = new HttpHeaders().set('Authorization', 'Bearer [OAUTH2_TOKEN]').set('Content-Type', 'audio');
    return this.http.post(url, {headers}).subscribe(res => console.log(res));
  }
}
