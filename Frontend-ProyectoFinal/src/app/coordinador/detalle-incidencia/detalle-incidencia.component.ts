import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IncidenciasService } from '../../core/services/incidencias.service';

@Component({
  selector: 'app-detalle-incidencia',
  templateUrl: './detalle-incidencia.component.html',
  styleUrl: './detalle-incidencia.component.scss'
})
export class DetalleIncidenciaComponent implements OnInit {
  incidencia: any;
  loading = true;
  updating = false;

  estadoForm!: FormGroup;
  successMessage = '';
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private incidenciasService: IncidenciasService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.estadoForm = this.fb.group({
      estado: ['', Validators.required]
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.cargarDetalle(+id);
    } else {
      this.router.navigate(['/coordinador/incidencias']);
    }
  }

  cargarDetalle(id: number) {
    this.loading = true;
    this.incidenciasService.obtenerPorId(id).subscribe({
      next: (data) => {
        this.incidencia = data;
        this.estadoForm.patchValue({ estado: data.estado });
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar la incidencia', err);
        this.router.navigate(['/coordinador/incidencias']);
      }
    });
  }

  actualizarEstado() {
    if (this.estadoForm.invalid || !this.incidencia) return;

    this.updating = true;
    this.successMessage = '';
    this.errorMessage = '';

    const nuevoEstado = this.estadoForm.value.estado;

    this.incidenciasService.cambiarEstado(this.incidencia.id, nuevoEstado).subscribe({
      next: (res) => {
        this.updating = false;
        this.successMessage = 'Estado actualizado correctamente.';
        this.incidencia = res;
        setTimeout(() => this.successMessage = '', 3000);
      },
      error: (err) => {
        this.updating = false;
        this.errorMessage = err.error?.message || 'No se pudo actualizar el estado.';
        console.error('Error al actualizar estado', err);
      }
    });
  }
}
