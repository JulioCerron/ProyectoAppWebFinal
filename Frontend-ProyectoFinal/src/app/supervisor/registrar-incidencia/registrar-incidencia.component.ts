import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IncidenciasService } from '../../core/services/incidencias.service';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-registrar-incidencia',
  templateUrl: './registrar-incidencia.component.html',
  styleUrl: './registrar-incidencia.component.scss'
})
export class RegistrarIncidenciaComponent implements OnInit {
  registrarForm!: FormGroup;
  loading = false;
  successMessage = '';
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private incidenciasService: IncidenciasService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.registrarForm = this.fb.group({
      titulo: ['', [Validators.required, Validators.minLength(5)]],
      descripcion: ['', [Validators.required, Validators.minLength(10)]],
      prioridad: ['', Validators.required],
      areaId: ['', Validators.required]
    });
  }

  registrar(): void {
    if (this.registrarForm.invalid) {
      this.registrarForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.successMessage = '';
    this.errorMessage = '';

    const newIncidencia = {
      ...this.registrarForm.value,
      areaId: parseInt(this.registrarForm.value.areaId, 10),
      reportadoPorId: this.authService.currentUserValue?.id || 1, // Fallback a 1 si no hay usuario id
      estado: 'REGISTRADA'
    };

    this.incidenciasService.registrar(newIncidencia).subscribe({
      next: (res: any) => {
        this.loading = false;
        this.successMessage = 'Incidencia registrada exitosamente. Código: INC-00' + res.id;
        this.registrarForm.reset({ prioridad: '', areaId: '' });
      },
      error: (err: any) => {
        this.loading = false;
        this.errorMessage = 'Ocurrió un error al registrar la incidencia. Inténtelo de nuevo.';
        console.error(err);
      }
    });
  }
}
