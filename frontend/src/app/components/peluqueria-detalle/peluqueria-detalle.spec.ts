import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PeluqueriaDetalleComponent } from './peluqueria-detalle';

describe('PeluqueriaDetalleComponent', () => {
  let component: PeluqueriaDetalleComponent;
  let fixture: ComponentFixture<PeluqueriaDetalleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PeluqueriaDetalleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PeluqueriaDetalleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
