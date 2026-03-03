import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PeluqueriaCardComponent } from './peluqueria-card';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('PeluqueriaCardComponent', () => {
  let component: PeluqueriaCardComponent;
  let fixture: ComponentFixture<PeluqueriaCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PeluqueriaCardComponent],
      providers: [
        provideRouter([]),
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PeluqueriaCardComponent);
    component = fixture.componentInstance;

    component.peluqueriaInfo = {
      id: 1,
      nombre: 'Peluquería Test Jest',
      direccion: 'Calle Falsa 123',
      email: 'test@test.com',
      telefono: 600000000
    };


    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
