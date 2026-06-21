export interface Empleado {
  id?: number;
  nombre: string;
  apellido: string;
  dni: string;
  cargo: string;
  departamentoId: number;
  nombreDepartamento?: string;
  activo?: boolean;
}
