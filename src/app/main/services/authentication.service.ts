import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../users';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { TestBed } from '@angular/core/testing';
import { ApiService } from './api.service';

const API_URL = environment.apiUrl;

@Injectable({
  providedIn: 'root'
})

export class AuthenticationService {

}
describe('AuthService', () => {
    beforeEach(() => TestBed.configureTestingModule({}));
  
    it('should be created', () => {
      const service: ApiService = TestBed.get(ApiService);
      expect(service).toBeTruthy();
    });
  });