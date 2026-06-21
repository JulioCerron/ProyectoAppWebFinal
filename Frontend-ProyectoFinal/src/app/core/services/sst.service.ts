import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface ObservacionDTO {
  id?: number;
  incidenciaId: number;
  descripcion: string;
  fechaRegistro?: string;
  observadoPor: string;
  nivelRiesgo: string;
}

export interface EvaluacionRiesgoDTO {
  id?: number;
  observacionId: number;
  nivelRiesgo: string;
  medidasPreventivas?: string;
  aprobado?: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class SstService {
  private apiUrl = `${environment.apiUrl}/sst`;

  constructor(private http: HttpClient) { }

  crearObservacion(observacion: ObservacionDTO): Observable<ObservacionDTO> {
    return this.http.post<ObservacionDTO>(`${this.apiUrl}/observaciones`, observacion);
  }

  listarPorIncidencia(incidenciaId: number): Observable<ObservacionDTO[]> {
    return this.http.get<ObservacionDTO[]>(`${this.apiUrl}/incidencia/${incidenciaId}/observaciones`);
  }

  actualizarRiesgo(id: number, nuevoNivel: string): Observable<EvaluacionRiesgoDTO> {
    return this.http.put<EvaluacionRiesgoDTO>(`${this.apiUrl}/observaciones/${id}/riesgo?nuevoNivel=${nuevoNivel}`, {});
  }

  completarEvaluacion(observacionId: number, dto: EvaluacionRiesgoDTO): Observable<EvaluacionRiesgoDTO> {
    return this.http.patch<EvaluacionRiesgoDTO>(`${this.apiUrl}/evaluaciones/${observacionId}`, dto);
  }
}
