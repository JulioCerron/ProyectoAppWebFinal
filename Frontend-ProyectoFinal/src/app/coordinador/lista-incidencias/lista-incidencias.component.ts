import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IncidenciasService } from '../../core/services/incidencias.service';
import { EmpleadosService } from '../../core/services/empleados.service';

@Component({
  selector: 'app-lista-incidencias',
  templateUrl: './lista-incidencias.component.html',
  styleUrl: './lista-incidencias.component.scss'
})
export class ListaIncidenciasComponent implements OnInit {
  incidencias: any[] = [];
  incidenciasFiltradas: any[] = [];
  empleados: any[] = [];
  loading = true;
  assigning = false;

  filtroTexto = '';
  filtroArea = '';
  filtroEstado = '';
  filtroPrioridad = '';

  selectedIncidencia: any = null;
  assignForm!: FormGroup;

  successMessage = '';
  errorMessage = '';

  constructor(
    private incidenciasService: IncidenciasService,
    private empleadosService: EmpleadosService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.assignForm = this.fb.group({
      empleadoId: ['', Validators.required]
    });
    this.cargarDatos();
  }

  cargarDatos() {
    this.loading = true;
    this.incidenciasService.listarTodas().subscribe({
      next: (data) => {
        this.incidencias = data;
        this.aplicarFiltros();
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando incidencias', err);
        this.loading = false;
      }
    });

    this.empleadosService.listar().subscribe({
      next: (data) => this.empleados = data,
      error: (err) => console.error('Error cargando empleados', err)
    });
  }

  aplicarFiltros() {
    this.incidenciasFiltradas = this.incidencias.filter(i => {
      const matchTexto = !this.filtroTexto || 
        i.titulo?.toLowerCase().includes(this.filtroTexto.toLowerCase()) || 
        (`INC-00${i.id}`).toLowerCase().includes(this.filtroTexto.toLowerCase());
      
      const matchArea = !this.filtroArea || this.filtroArea === 'Todas las áreas' || 
        (this.filtroArea === 'Operaciones' && i.areaId === 1) ||
        (this.filtroArea === 'Logística' && i.areaId === 2) ||
        (this.filtroArea === 'SST' && i.areaId === 3);

      const matchEstado = !this.filtroEstado || this.filtroEstado === 'Todos los estados' || i.estado === this.filtroEstado;
      const matchPrioridad = !this.filtroPrioridad || this.filtroPrioridad === 'Toda prioridad' || i.prioridad === this.filtroPrioridad;

      return matchTexto && matchArea && matchEstado && matchPrioridad;
    });
  }

  abrirAsignar(incidencia: any) {
    this.selectedIncidencia = incidencia;
    this.assignForm.reset();
  }

  asignarResponsable() {
    if (this.assignForm.invalid || !this.selectedIncidencia) return;

    this.assigning = true;
    this.successMessage = '';
    this.errorMessage = '';

    const empleadoId = this.assignForm.value.empleadoId;

    this.incidenciasService.asignar(this.selectedIncidencia.id, empleadoId).subscribe({
      next: (res) => {
        this.assigning = false;
        this.successMessage = 'Responsable asignado correctamente.';
        this.cargarDatos(); // recargar para actualizar el estado
        setTimeout(() => this.successMessage = '', 3000);
      },
      error: (err) => {
        this.assigning = false;
        this.errorMessage = 'Hubo un error al asignar el responsable.';
        console.error('Error asignando', err);
      }
    });
  }
}
