// 1. LA NUEVA FORMA OFICIAL DE INICIALIZAR ANGULAR CON JEST
import { setupZoneTestEnv } from 'jest-preset-angular/setup-env/zone';

// Ejecutamos la función que arranca todo el motor de Angular por debajo
setupZoneTestEnv();

// 2. Mocks globales para el navegador (evita errores en consola)
Object.defineProperty(window, 'scrollTo', { value: () => {}, writable: true });
Object.defineProperty(window, 'alert', { value: jest.fn(), writable: true });

// 3. Mock global de SweetAlert2 (usando sintaxis compatible con Jest)
jest.mock('sweetalert2', () => ({
  fire: jest.fn().mockResolvedValue({ isConfirmed: true })
}));