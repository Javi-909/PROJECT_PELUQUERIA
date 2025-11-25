import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PeluqueriaForm } from './peluqueria-form';

describe('PeluqueriaForm', () => {
  let component: PeluqueriaForm;
  let fixture: ComponentFixture<PeluqueriaForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PeluqueriaForm]
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
