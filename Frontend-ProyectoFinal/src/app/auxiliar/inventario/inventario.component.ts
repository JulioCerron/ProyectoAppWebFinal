import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LogisticaService, MaterialDTO } from '../../core/services/logistica.service';

@Component({
  selector: 'app-inventario',
  templateUrl: './inventario.component.html',
  styleUrl: './inventario.component.scss'
})
export class InventarioComponent implements OnInit {
  materiales: MaterialDTO[] = [];
  materialesFiltrados: MaterialDTO[] = [];
  loading = false;
  
  filtroTexto = '';
  
  materialForm!: FormGroup;
  submitLoading = false;
  successMessage = '';
  errorMessage = '';

  constructor(
    private logisticaService: LogisticaService,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.materialForm = this.formBuilder.group({
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required],
      stockActual: [0, [Validators.required, Validators.min(0)]],
      stockMinimo: [0, [Validators.required, Validators.min(0)]],
      unidadMedida: ['UND', Validators.required]
    });

    this.cargarInventario();
  }

  cargarInventario() {
    this.loading = true;
    this.logisticaService.listarInventario().subscribe({
      next: (data) => {
        this.materiales = data;
        this.aplicarFiltros();
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando inventario', err);
        this.loading = false;
      }
    });
  }

  aplicarFiltros() {
    this.materialesFiltrados = this.materiales.filter(m => {
      return !this.filtroTexto || 
        m.nombre.toLowerCase().includes(this.filtroTexto.toLowerCase()) ||
        m.descripcion.toLowerCase().includes(this.filtroTexto.toLowerCase());
    });
  }

  prepararNuevo() {
    this.materialForm.reset({ stockActual: 0, stockMinimo: 0, unidadMedida: 'UND' });
    this.successMessage = '';
    this.errorMessage = '';
  }

  registrarMaterial() {
    if (this.materialForm.invalid) return;

    this.submitLoading = true;
    this.successMessage = '';
    this.errorMessage = '';

    const payload: MaterialDTO = this.materialForm.value;

    this.logisticaService.registrarMaterial(payload).subscribe({
      next: (res) => {
        this.submitLoading = false;
        this.successMessage = 'Material registrado con éxito.';
        this.cargarInventario(); // Recarga
        setTimeout(() => this.successMessage = '', 3000);
      },
      error: (err) => {
        this.submitLoading = false;
        this.errorMessage = 'Hubo un error al registrar el material.';
      }
    });
  }
}
