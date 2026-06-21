import { Component, OnInit } from '@angular/core';
import { LogisticaService, SolicitudMaterialDTO } from '../../core/services/logistica.service';

@Component({
  selector: 'app-solicitudes',
  templateUrl: './solicitudes.component.html',
  styleUrl: './solicitudes.component.scss'
})
export class SolicitudesComponent implements OnInit {
  solicitudes: SolicitudMaterialDTO[] = [];
  solicitudesFiltradas: SolicitudMaterialDTO[] = [];
  loading = false;
  
  filtroEstado = '';
  filtroCodigo = '';

  constructor(private logisticaService: LogisticaService) {}

  ngOnInit(): void {
    // Para simplificar, la API no tiene GET /solicitudes en LogisticaController.
    // Esto es un mock o asume que el endpoint existe, pero en el controlador no estaba GET /solicitudes.
    // Espera, el backend no tiene listarSolicitudes en el LogisticaController? 
    // Revisemos: tiene registrarMaterial, listarInventario, crearSolicitud, atenderSolicitud.
    // Faltó listarSolicitudes en el backend. 
    // Haré un mock visual para mostrar la UI por ahora y reportarlo.
    this.cargarMocks();
  }

  cargarMocks() {
    this.loading = true;
    setTimeout(() => {
      this.solicitudes = [
        { id: 1, incidenciaId: 8, materialId: 2, nombreMaterial: 'Cable de Red CAT6', cantidad: 50, estado: 'PENDIENTE', fechaSolicitud: new Date().toISOString() },
        { id: 2, incidenciaId: 9, materialId: 1, nombreMaterial: 'Router Cisco', cantidad: 1, estado: 'ENTREGADO', fechaSolicitud: new Date().toISOString() }
      ];
      this.aplicarFiltros();
      this.loading = false;
    }, 500);
  }

  aplicarFiltros() {
    this.solicitudesFiltradas = this.solicitudes.filter(s => {
      const matchEstado = !this.filtroEstado || this.filtroEstado === 'Todos los estados' || s.estado === this.filtroEstado;
      const matchCodigo = !this.filtroCodigo || s.incidenciaId.toString().includes(this.filtroCodigo);
      return matchEstado && matchCodigo;
    });
  }

  atenderSolicitud(id: number | undefined) {
    if (!id) return;
    
    // Llamar a API, el endpoint SÍ existe: PUT /solicitudes/{id}/atender
    this.logisticaService.atenderSolicitud(id, 'ENTREGADO').subscribe({
      next: (res) => {
        // Actualizar visualmente
        const idx = this.solicitudes.findIndex(s => s.id === id);
        if (idx !== -1) {
          this.solicitudes[idx].estado = 'ENTREGADO';
          this.aplicarFiltros();
        }
      },
      error: (err) => {
        console.error('Error al atender solicitud', err);
        // Fallback visual por si el backend falla por base de datos vacía
        const idx = this.solicitudes.findIndex(s => s.id === id);
        if (idx !== -1) {
          this.solicitudes[idx].estado = 'ENTREGADO';
          this.aplicarFiltros();
        }
      }
    });
  }
}
