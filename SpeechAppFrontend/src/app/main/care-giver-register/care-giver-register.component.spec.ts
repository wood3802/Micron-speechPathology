import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CareGiverRegisterComponent } from './care-giver-register.component';

describe('CareGiverRegisterComponent', () => {
  let component: CareGiverRegisterComponent;
  let fixture: ComponentFixture<CareGiverRegisterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CareGiverRegisterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CareGiverRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
