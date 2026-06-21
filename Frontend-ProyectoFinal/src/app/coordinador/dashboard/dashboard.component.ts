import { Component, OnInit } from '@angular/core';
import { IncidenciasService } from '../../core/services/incidencias.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  incidencias: any[] = [];
  loading = true;

  // KPIs
  registradas = 0;
  asignadas = 0;
  enProceso = 0;
  atendidas = 0;
  cerradas = 0;
  total = 0;

  constructor(private incidenciasService: IncidenciasService) {}

  ngOnInit(): void {
    this.cargarIncidencias();
  }

  cargarIncidencias() {
    this.incidenciasService.listarTodas().subscribe({
      next: (data) => {
        this.incidencias = data;
        this.calcularKpis();
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando incidencias', err);
        this.loading = false;
      }
    });
  }

  calcularKpis() {
    this.total = this.incidencias.length;
    this.registradas = this.incidencias.filter(i => i.estado === 'REGISTRADA').length;
    this.asignadas = this.incidencias.filter(i => i.estado === 'ASIGNADA').length;
    this.enProceso = this.incidencias.filter(i => i.estado === 'EN_PROCESO').length;
    this.atendidas = this.incidencias.filter(i => i.estado === 'ATENDIDA').length;
    this.cerradas = this.incidencias.filter(i => i.estado === 'CERRADA').length;
  }
}
