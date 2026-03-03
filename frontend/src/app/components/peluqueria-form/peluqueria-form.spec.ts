import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PeluqueriaForm } from './peluqueria-form';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('PeluqueriaForm', () => {
  let component: PeluqueriaForm;
  let fixture: ComponentFixture<PeluqueriaForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PeluqueriaForm],
      providers: [
        provideRouter([]),
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PeluqueriaForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
