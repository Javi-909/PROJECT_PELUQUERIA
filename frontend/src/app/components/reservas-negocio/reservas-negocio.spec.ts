import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReservasNegocio } from './reservas-negocio';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('ReservasNegocio', () => {
  let component: ReservasNegocio;
  let fixture: ComponentFixture<ReservasNegocio>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservasNegocio],
      providers: [
        provideRouter([]),
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReservasNegocio);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
