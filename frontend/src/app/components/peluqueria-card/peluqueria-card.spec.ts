import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PeluqueriaCardComponent } from './peluqueria-card';

describe('PeluqueriaCardComponent', () => {
  let component: PeluqueriaCardComponent;
  let fixture: ComponentFixture<PeluqueriaCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PeluqueriaCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PeluqueriaCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
