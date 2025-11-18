import { TestBed } from '@angular/core/testing';

import { Peluqueria } from './peluqueria';

describe('Peluqueria', () => {
  let service: Peluqueria;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Peluqueria);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
