import { Component, OnInit } from '@angular/core';
import { IncidenciasService } from '../../core/services/incidencias.service';

@Component({
  selector: 'app-evaluaciones',
  templateUrl: './evaluaciones.component.html',
  styleUrl: './evaluaciones.component.scss'
})
export class EvaluacionesComponent implements OnInit {
  incidencias: any[] = [];
  incidenciasFiltradas: any[] = [];
  loading = false;
  filtroCodigo = '';

  constructor(private incidenciasService: IncidenciasService) {}

  ngOnInit(): void {
    this.cargarIncidencias();
  }

  cargarIncidencias() {
    this.loading = true;
    this.incidenciasService.listarTodas().subscribe({
      next: (data) => {
        // Mostramos las que necesitan evaluación o que aún no están cerradas
        this.incidencias = data.filter(i => i.estado === 'REGISTRADA' || i.estado === 'ASIGNADA' || i.estado === 'EN_PROCESO');
        this.aplicarFiltros();
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando', err);
        this.loading = false;
      }
    });
  }

  aplicarFiltros() {
    this.incidenciasFiltradas = this.incidencias.filter(i => {
      return !this.filtroCodigo || i.codigo?.toLowerCase().includes(this.filtroCodigo.toLowerCase());
    });
  }
}
