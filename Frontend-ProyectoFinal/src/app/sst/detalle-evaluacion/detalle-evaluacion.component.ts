import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IncidenciasService } from '../../core/services/incidencias.service';
import { SstService, ObservacionDTO } from '../../core/services/sst.service';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-detalle-evaluacion',
  templateUrl: './detalle-evaluacion.component.html',
  styleUrl: './detalle-evaluacion.component.scss'
})
export class DetalleEvaluacionComponent implements OnInit {
  incidenciaId!: number;
  incidencia: any;
  observaciones: ObservacionDTO[] = [];
  
  evalForm!: FormGroup;
  loading = false;
  submitLoading = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder,
    private incidenciasService: IncidenciasService,
    private sstService: SstService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.incidenciaId = Number(this.route.snapshot.paramMap.get('id'));
    
    this.evalForm = this.formBuilder.group({
      nivelRiesgo: ['', Validators.required],
      descripcion: ['', Validators.required]
    });

    this.cargarDatos();
  }

  cargarDatos() {
    this.loading = true;
    
    // Cargar incidencia
    this.incidenciasService.obtenerPorId(this.incidenciaId).subscribe({
      next: (data) => {
        this.incidencia = data;
        
        // Cargar observaciones
        this.sstService.listarPorIncidencia(this.incidenciaId).subscribe({
          next: (obs) => {
            this.observaciones = obs;
            this.loading = false;
          },
          error: (err) => {
            console.error(err);
            this.loading = false;
          }
        });
      },
      error: (err) => {
        this.errorMessage = 'No se pudo cargar la incidencia.';
        this.loading = false;
      }
    });
  }

  onSubmit() {
    if (this.evalForm.invalid) return;

    this.submitLoading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const val = this.evalForm.value;
    const user = this.authService.currentUserValue;
    
    const dto: ObservacionDTO = {
      incidenciaId: this.incidenciaId,
      descripcion: val.descripcion,
      observadoPor: user?.nombreUsuario || 'SST',
      nivelRiesgo: val.nivelRiesgo
    };

    this.sstService.crearObservacion(dto).subscribe({
      next: (res) => {
        this.successMessage = 'Evaluación de riesgo registrada con éxito.';
        this.submitLoading = false;
        this.evalForm.reset();
        this.cargarDatos(); // recargar
      },
      error: (err) => {
        this.errorMessage = 'Hubo un error al registrar la evaluación.';
        this.submitLoading = false;
      }
    });
  }
}
