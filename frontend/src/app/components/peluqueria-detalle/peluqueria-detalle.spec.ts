import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PeluqueriaDetalleComponent } from './peluqueria-detalle';
import { ActivatedRoute } from '@angular/router';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('PeluqueriaDetalleComponent', () => {
  let component: PeluqueriaDetalleComponent;
  let fixture: ComponentFixture<PeluqueriaDetalleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PeluqueriaDetalleComponent],
      providers: [ provideRouter([]), provideHttpClient(), provideHttpClientTesting()
    ]
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
