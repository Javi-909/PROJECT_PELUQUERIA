export type EstadoReserva = 'PENDIENTE' | 'CONFIRMADA' | 'CANCELADA';

export interface ReservaNegocio {
  id: number;
  fecha: string;
  hora: string;
  nombreCliente: string;
  emailCliente: string;
  nombreServicio: string;
  precioServicio: number;
  estado: EstadoReserva;
}