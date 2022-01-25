import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeverageListComponent } from './beverage-list.component';

describe('BeverageListComponent', () => {
  let component: BeverageListComponent;
  let fixture: ComponentFixture<BeverageListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BeverageListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BeverageListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
