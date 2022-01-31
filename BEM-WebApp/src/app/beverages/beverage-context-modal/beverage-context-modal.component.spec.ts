import {ComponentFixture, TestBed} from '@angular/core/testing';

import {BeverageContextModalComponent} from './beverage-context-modal.component';

describe('BeverageContextModalComponent', () => {
  let component: BeverageContextModalComponent;
  let fixture: ComponentFixture<BeverageContextModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BeverageContextModalComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BeverageContextModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
