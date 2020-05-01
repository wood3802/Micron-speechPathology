import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpeechServiceComponent } from './speech-service.component';

describe('SpeechServiceComponent', () => {
  let component: SpeechServiceComponent;
  let fixture: ComponentFixture<SpeechServiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpeechServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpeechServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
