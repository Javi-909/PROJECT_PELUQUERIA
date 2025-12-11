import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservasNegocio } from './reservas-negocio';

describe('ReservasNegocio', () => {
  let component: ReservasNegocio;
  let fixture: ComponentFixture<ReservasNegocio>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservasNegocio]
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
