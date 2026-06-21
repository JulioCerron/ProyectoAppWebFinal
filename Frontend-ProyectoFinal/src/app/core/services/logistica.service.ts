import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface MaterialDTO {
  id?: number;
  nombre: string;
  descripcion: string;
  stockActual: number;
  stockMinimo: number;
  unidadMedida: string;
}

export interface SolicitudMaterialDTO {
  id?: number;
  incidenciaId: number;
  materialId: number;
  nombreMaterial?: string;
  cantidad: number;
  estado?: string;
  fechaSolicitud?: string;
}

@Injectable({
  providedIn: 'root'
})
export class LogisticaService {
  private apiUrl = `${environment.apiUrl}/logistica`;

  constructor(private http: HttpClient) { }

  registrarMaterial(material: MaterialDTO): Observable<MaterialDTO> {
    return this.http.post<MaterialDTO>(`${this.apiUrl}/inventario`, material);
  }

  listarInventario(): Observable<MaterialDTO[]> {
    return this.http.get<MaterialDTO[]>(`${this.apiUrl}/inventario`);
  }

  crearSolicitud(solicitud: SolicitudMaterialDTO): Observable<SolicitudMaterialDTO> {
    return this.http.post<SolicitudMaterialDTO>(`${this.apiUrl}/solicitudes`, solicitud);
  }

  atenderSolicitud(id: number, estado: string): Observable<SolicitudMaterialDTO> {
    return this.http.put<SolicitudMaterialDTO>(`${this.apiUrl}/solicitudes/${id}/atender?estado=${estado}`, {});
  }
}
