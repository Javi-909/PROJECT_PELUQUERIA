import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NuevoServicio } from './nuevo-servicio';
import { PeluqueriaService } from '../../services/peluqueria';
import { ServicioPeluService } from '../../services/serviciopelu';
import { ActivatedRoute, Router, provideRouter } from '@angular/router';
import { of, throwError } from 'rxjs';
import { FormsModule } from '@angular/forms';

describe('NuevoServicio Component', () => {
  let component: NuevoServicio;
  let fixture: ComponentFixture<NuevoServicio>;
  let router: Router;
  
  // Mocks de servicios de datos
  let mockPeluService: any;
  let mockServicioPeluService: any;

  // Datos de prueba
  const serviciosFalsos = [
    { id: 1, nombre: 'Corte Caballero' },
    { id: 2, nombre: 'Tinte' }
  ];

  beforeEach(async () => {
    mockPeluService = {
      getServiciosDisponibles: jasmine.createSpy('getServiciosDisponibles').and.returnValue(of(serviciosFalsos))
    };

    mockServicioPeluService = {
      addServicio: jasmine.createSpy('addServicio').and.returnValue(of({}))
    };

    await TestBed.configureTestingModule({
      imports: [NuevoServicio, FormsModule],
      providers: [
        // 1. Configuramos el sistema de rutas real para evitar el error 'root'
        provideRouter([]),
        { provide: PeluqueriaService, useValue: mockPeluService },
        { provide: ServicioPeluService, useValue: mockServicioPeluService },
        // 2. Mock de la ruta activa para capturar el ID de la URL
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: (key: string) => key === 'peluqueriaId' ? '10' : null
              }
            }
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(NuevoServicio);
    component = fixture.componentInstance;

    // 3. Inyectamos el router real y espiamos su método de navegación
    router = TestBed.inject(Router);
    spyOn(router, 'navigate');
  });

  it('debería crearse correctamente', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  it('debería inicializar peluqueriaId y cargar el catálogo al inicio', () => {
    fixture.detectChanges(); 

    expect(component.peluqueriaId).toBe(10);
    expect(mockPeluService.getServiciosDisponibles).toHaveBeenCalled();
    expect(component.serviciosGenericos.length).toBe(2);
  });

  it('debería mostrar un alert si los datos del formulario son inválidos al guardar', () => {
    spyOn(window, 'alert');
    fixture.detectChanges();

    // Caso: Sin servicio seleccionado
    component.nuevoServicio.servicioId = null;
    component.guardar();
    expect(window.alert).toHaveBeenCalledWith("Por favor, selecciona un tipo de servicio.");

    // Caso: Precio inválido
    component.nuevoServicio.servicioId = 1 as any;
    component.nuevoServicio.precio = 0;
    component.guardar();
    expect(window.alert).toHaveBeenCalledWith("El precio y la duración deben ser mayores que 0.");
  });

  it('debería llamar al servicio y navegar cuando el guardado es exitoso', () => {
    spyOn(window, 'alert');
    fixture.detectChanges();

    // Rellenamos datos correctos
    component.nuevoServicio.servicioId = 1 as any;
    component.nuevoServicio.precio = 25;
    component.nuevoServicio.duracion = 45;

    component.guardar();

    // Verificamos el envío de datos al backend
    expect(mockServicioPeluService.addServicio).toHaveBeenCalledWith({
      peluqueriaId: 10,
      servicioId: 1,
      precio: 25,
      duracion: 45
    });

    expect(window.alert).toHaveBeenCalledWith("¡Servicio añadido correctamente!");
    // Verificamos la navegación usando el espía del router real
    expect(router.navigate).toHaveBeenCalledWith(['/peluqueria', 10]);
  });

  it('debería manejar errores del servidor al guardar', () => {
    spyOn(window, 'alert');
    fixture.detectChanges();

    // Simulamos fallo
    mockServicioPeluService.addServicio.and.returnValue(throwError(() => new Error('API Error')));

    component.nuevoServicio.servicioId = 1 as any;
    component.nuevoServicio.precio = 20;
    component.guardar();

    expect(window.alert).toHaveBeenCalledWith("Error al guardar. Revisa la consola.");
  });
});