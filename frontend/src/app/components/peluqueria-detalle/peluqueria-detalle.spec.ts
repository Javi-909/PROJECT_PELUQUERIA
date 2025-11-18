import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PeluqueriaDetalle } from './peluqueria-detalle';

describe('PeluqueriaDetalle', () => {
  let component: PeluqueriaDetalle;
  let fixture: ComponentFixture<PeluqueriaDetalle>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PeluqueriaDetalle]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PeluqueriaDetalle);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
