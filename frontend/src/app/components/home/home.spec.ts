import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeComponent } from './home';
import { PeluqueriaService } from '../../services/peluqueria';
import { Auth } from '../../services/auth';
import { of } from 'rxjs';
import { provideRouter } from '@angular/router';

// 1. Importamos las herramientas de ts-mockito
import { mock, when, instance, verify } from 'ts-mockito';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  // 2. Declaramos los mocks con su TIPO REAL
  let mockPeluService: PeluqueriaService;
  let mockAuthService: Auth;

  const peluqueriasFalsas = [
    { id: 1, nombre: 'Peluquería Alex', direccion: 'Calle 1', email: 'a@a.com', telefono: 123 }
  ];

  beforeEach(async () => {
    // 3. Inicializamos los mocks
    mockPeluService = mock(PeluqueriaService);
    mockAuthService = mock(Auth);

    // 4. Esto es lo que van a responder cuando el componente los llame
    when(mockPeluService.getPeluquerias()).thenReturn(of(peluqueriasFalsas));
    when(mockAuthService.isNegocio()).thenReturn(false);
    when(mockAuthService.isLoggedIn()).thenReturn(true);

    await TestBed.configureTestingModule({
      imports: [HomeComponent],
      providers: [
        provideRouter([]),
        // 5. Inyectamos la "instancia" del mock en Angular
        { provide: PeluqueriaService, useFactory: () => instance(mockPeluService) },
        { provide: Auth, useFactory: () => instance(mockAuthService) }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
  });

  it('debería crearse el componente', () => {
    fixture.detectChanges(); // Esto dispara el ngOnInit
    expect(component).toBeTruthy();
  });

  it('debería cargar las peluquerías al iniciar', () => {
    fixture.detectChanges(); 
    
    // 6. Verificamos que se haya llamado al servicio (Estilo ts-mockito)
    verify(mockPeluService.getPeluquerias()).once();
    
    expect(component.peluquerias.length).toBe(1);
    expect(component.peluquerias[0].nombre).toBe('Peluquería Alex');
  });
});