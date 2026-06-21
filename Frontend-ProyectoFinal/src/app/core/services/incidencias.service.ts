import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class IncidenciasService {
  private apiUrl = '/api/incidents';

  constructor(private http: HttpClient) {}

  listarTodas(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  obtenerPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  registrar(incidencia: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, incidencia);
  }

  asignar(id: number, empleadoId: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}/asignar/${empleadoId}`, {});
  }

  cambiarEstado(id: number, estado: string): Observable<any> {
    return this.http.patch<any>(`${this.apiUrl}/${id}/estado?nuevoEstado=${estado}`, {});
  }

  getMaterialesDisponibles(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/materiales-disponibles`);
  }

  solicitarMaterial(id: number, materialId: number, cantidad: number): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/${id}/solicitar-material?materialId=${materialId}&cantidad=${cantidad}`, {});
  }
}
