
export type EstadoReserva = 'PENDIENTE' | 'CONFIRMADA' | 'CANCELADA';

export interface ReservaDto {

  id?: number; // El ID de la reserva (se genera solo)
  estado?: EstadoReserva; 
  fecha: string;      // Formato: "YYYY-MM-DD" (LocalDate en Java)
  hora: string;       // Formato: "HH:mm" or "HH:mm:ss" (LocalTime en Java)
  
  clienteId: number;  
  peluqueriaId: number; 
  
  // --- Campo opcional de tu entidad ---
  idServicioPelu?: number; // Si reservas un servicio concreto (Corte, Tinte...)
}