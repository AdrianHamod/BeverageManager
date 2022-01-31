import {ComponentFixture, TestBed} from '@angular/core/testing';

import {BeverageDetailComponent} from './beverage-detail.component';

describe('BeverageDetailComponent', () => {
  let component: BeverageDetailComponent;
  let fixture: ComponentFixture<BeverageDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BeverageDetailComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BeverageDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
