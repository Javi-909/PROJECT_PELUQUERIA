import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { MiPerfil } from './mi-perfil';
import { Auth } from '../../services/auth';
import { ClienteService } from '../../services/cliente';
import { Router, provideRouter } from '@angular/router';
import { of, throwError } from 'rxjs';

describe('MiPerfil Component', () => {
  let component: MiPerfil;
  let fixture: ComponentFixture<MiPerfil>;
  let router: Router;
  
  // Mocks de servicios de datos
  let mockAuth: any;
  let mockClienteService: any;

  // Datos de prueba
  const clienteFalso = {
    id: 123,
    nombre: 'Alexo Prueba',
    email: 'alexo@test.com',
    genero: 'HOMBRE',
    password: ''
  };

  beforeEach(async () => {
    // 1. Mantenemos los mocks para la lógica de negocio
    mockAuth = {
      userId: 123,
      logout: jasmine.createSpy('logout')
    };

    mockClienteService = {
      getClienteById: jasmine.createSpy('getClienteById').and.returnValue(of(clienteFalso)),
      updateCliente: jasmine.createSpy('updateCliente').and.returnValue(of({}))
    };

    await TestBed.configureTestingModule({
      imports: [MiPerfil],
      providers: [
        // 2. Usamos provideRouter([]) real para que los routerLink no exploten
        provideRouter([]),
        { provide: Auth, useValue: mockAuth },
        { provide: ClienteService, useValue: mockClienteService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(MiPerfil);
    component = fixture.componentInstance;

    // 3. Inyectamos el router real del entorno de pruebas y espiamos su método navigate
    router = TestBed.inject(Router);
    spyOn(router, 'navigate');
  });

  it('debería crearse correctamente', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  it('debería cargar los datos del cliente al iniciar (ngOnInit)', () => {
    fixture.detectChanges(); 

    expect(mockClienteService.getClienteById).toHaveBeenCalledWith(123);
    expect(component.cliente.nombre).toBe('Alexo Prueba');
    // Verificamos que la password se limpia por seguridad visual como hace tu .ts
    expect(component.cliente.password).toBe(''); 
  });

  it('debería mostrar un mensaje de éxito y borrarlo a los 3 segundos al guardar cambios', fakeAsync(() => {
    fixture.detectChanges();
    
    component.guardarCambios();
    
    expect(mockClienteService.updateCliente).toHaveBeenCalled();
    expect(component.successMsg).toBe('¡Perfil actualizado correctamente!');

    tick(3000); // Avanzamos el tiempo virtual del setTimeout
    expect(component.successMsg).toBe('');
  }));

  it('debería mostrar un alert si falla la actualización', () => {
    spyOn(window, 'alert');
    mockClienteService.updateCliente.and.returnValue(throwError(() => new Error('Error de red')));
    
    fixture.detectChanges();
    component.guardarCambios();

    expect(window.alert).toHaveBeenCalledWith('Error al actualizar el perfil');
  });

  it('debería cerrar sesión correctamente llamando al servicio Auth', () => {
    fixture.detectChanges();
    component.cerrarSesion();
    
    expect(mockAuth.logout).toHaveBeenCalled();
  });
});