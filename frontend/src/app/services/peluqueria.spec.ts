import { TestBed } from '@angular/core/testing';

import { PeluqueriaService } from './peluqueria';

describe('Peluqueria', () => {
  let service: PeluqueriaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PeluqueriaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
