import { Component, OnInit } from '@angular/core';
import { IncidenciasService } from '../../core/services/incidencias.service';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-mis-incidencias',
  templateUrl: './mis-incidencias.component.html'
})
export class MisIncidenciasComponent implements OnInit {
  incidencias: any[] = [];
  incidenciasFiltradas: any[] = [];
  loading = true;

  filtroTexto = '';
  filtroEstado = '';
  filtroPrioridad = '';

  constructor(
    private incidenciasService: IncidenciasService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos() {
    this.loading = true;
    this.incidenciasService.listarTodas().subscribe({
      next: (data) => {
        const currentUser = this.authService.currentUserValue;
        if (currentUser && currentUser.id) {
          this.incidencias = data.filter((i: any) => i.reportadoPorId === currentUser.id);
        } else {
          // Si por alguna razón no hay ID, mostramos todas (o podríamos mostrar ninguna)
          this.incidencias = data;
        }
        this.aplicarFiltros();
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando incidencias', err);
        this.loading = false;
      }
    });
  }

  aplicarFiltros() {
    this.incidenciasFiltradas = this.incidencias.filter(i => {
      const matchTexto = !this.filtroTexto || 
        i.titulo?.toLowerCase().includes(this.filtroTexto.toLowerCase()) || 
        (`INC-00${i.id}`).toLowerCase().includes(this.filtroTexto.toLowerCase());

      const matchEstado = !this.filtroEstado || this.filtroEstado === 'Todos los estados' || i.estado === this.filtroEstado;
      const matchPrioridad = !this.filtroPrioridad || this.filtroPrioridad === 'Toda prioridad' || i.prioridad === this.filtroPrioridad;

      return matchTexto && matchEstado && matchPrioridad;
    });
  }
}
