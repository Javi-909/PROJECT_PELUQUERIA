//espejo de mi clase PeluqueriaDto en el backend

export interface Peluqueria {
  nombre: string;
  email: string;
  direccion: string;
  telefono: number; // 'int' en Java se convierte en 'number' en TypeScript
}