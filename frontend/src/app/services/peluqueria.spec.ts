import { TestBed } from '@angular/core/testing';
import { PeluqueriaService } from './peluqueria';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('PeluqueriaService', () => {
  let service: PeluqueriaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PeluqueriaService, provideHttpClient(), provideHttpClientTesting()]
    });
    service = TestBed.inject(PeluqueriaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
