import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeComponent } from './home'; 
import { PeluqueriaService } from '../../services/peluqueria'; 
// IMPORTANTE: Cambiamos Auth por AuthService para que coincida con el componente
import { Auth } from '../../services/auth'; 
import { provideRouter } from '@angular/router';
import { of } from 'rxjs';

fdescribe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  let mockPeluqueriaService: any;
  let mockAuthService: any;

  const peluqueriasFalsas = [
    { id: 1, nombre: 'Peluquería Juan', direccion: 'Calle Mayor 1', email: 'juan@test.com', telefono: 111 },
    { id: 2, nombre: 'Barbería Moderna', direccion: 'Avenida Sol 2', email: 'barber@test.com', telefono: 222 }
  ];

  beforeEach(async () => {
    // Definimos el comportamiento simulado del servicio de peluquerías
    mockPeluqueriaService = {
      getPeluquerias: jasmine.createSpy('getPeluquerias').and.returnValue(of(peluqueriasFalsas))
    };

    // Definimos el comportamiento simulado del servicio de Auth
    // DEBE TENER todas las funciones que usa el HTML de Home
    mockAuthService = {
      isNegocio: jasmine.createSpy('isNegocio').and.returnValue(false),
      isCliente: jasmine.createSpy('isCliente').and.returnValue(true),
      isLoggedIn: jasmine.createSpy('isLoggedIn').and.returnValue(true),
      logout: jasmine.createSpy('logout'),
      userId: 1
    };

    await TestBed.configureTestingModule({
      imports: [HomeComponent],
      providers: [
        provideRouter([]),
        { provide: PeluqueriaService, useValue: mockPeluqueriaService },
        // CLAVE: Usamos AuthService aquí para que Angular lo sustituya correctamente
        { provide: Auth, useValue: mockAuthService } 
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
  });

  it('debería crearse correctamente', () => {
    fixture.detectChanges(); 
    expect(component).toBeTruthy();
  });

  it('debería cargar TODAS las peluquerías si el usuario NO es negocio', () => {
    fixture.detectChanges(); 
    expect(mockPeluqueriaService.getPeluquerias).toHaveBeenCalled();
    expect(component.peluquerias.length).toBe(2);
  });

  it('debería filtrar y mostrar SOLO su peluquería si el usuario ES negocio', () => {
    mockAuthService.isNegocio.and.returnValue(true);
    mockAuthService.userId = 2; 
    
    // Forzamos la recarga de la lógica
    component.cargarPeluquerias();
    fixture.detectChanges(); 
    
    expect(component.peluquerias.length).toBe(1);
    expect(component.peluquerias[0].id).toBe(2);
    expect(component.peluquerias[0].nombre).toBe('Barbería Moderna');
  });

  it('debería filtrar resultados cuando el usuario busca por nombre', () => {
    fixture.detectChanges(); 
    component.searchTerm = 'Juan'; 
    component.filtrarResultados();
    
    expect(component.peluqueriasFiltradas.length).toBe(1);
    expect(component.peluqueriasFiltradas[0].nombre).toContain('Juan');
  });

  it('debería mostrar cero resultados si la búsqueda no coincide con nada', () => {
    fixture.detectChanges();
    component.searchTerm = 'Zapatos';
    component.filtrarResultados();
    
    expect(component.peluqueriasFiltradas.length).toBe(0);
  });
});