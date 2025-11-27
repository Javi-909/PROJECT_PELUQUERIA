import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NuevoServicio } from './nuevo-servicio';

describe('NuevoServicio', () => {
  let component: NuevoServicio;
  let fixture: ComponentFixture<NuevoServicio>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NuevoServicio]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NuevoServicio);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
