import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeveragePageComponent } from './beverage-page.component';

describe('BeveragePageComponent', () => {
  let component: BeveragePageComponent;
  let fixture: ComponentFixture<BeveragePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BeveragePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BeveragePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
