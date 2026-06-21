export interface LoginRequest {
  nombreUsuario: string;
  contrasena: string;
}

export interface AuthResponse {
  token: string;
  id?: number;
  nombre?: string;
  nombreUsuario?: string;
  rol?: string;
}
