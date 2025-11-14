import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PeluqueriaCard } from './peluqueria-card';

describe('PeluqueriaCard', () => {
  let component: PeluqueriaCard;
  let fixture: ComponentFixture<PeluqueriaCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PeluqueriaCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PeluqueriaCard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
