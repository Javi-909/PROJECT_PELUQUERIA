import { TestBed } from '@angular/core/testing';
import { ReservaService } from './reserva';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('ReservaService', () => {
  let service: ReservaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ReservaService, provideHttpClient(), provideHttpClientTesting()]
    });
    service = TestBed.inject(ReservaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
