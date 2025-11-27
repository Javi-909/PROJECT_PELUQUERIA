export interface ServicioPeluCreacionDto {
  peluqueriaId: number; // El ID de la peluquería donde estamos
  servicioId: number;   // El ID del servicio genérico (ej: Corte)
  precio: number;
  duracion: number;
}