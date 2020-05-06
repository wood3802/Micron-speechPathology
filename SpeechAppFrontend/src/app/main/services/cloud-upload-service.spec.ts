import { TestBed } from '@angular/core/testing';

import { CloudUploadService } from './cloud-upload-service';

describe('CloudUploadService', () => {
  let service: CloudUploadService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CloudUploadService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
