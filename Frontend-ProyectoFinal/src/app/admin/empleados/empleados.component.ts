import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmpleadosService } from '../../core/services/empleados.service';
import { Empleado } from '../../core/models/empleado.models';

@Component({
  selector: 'app-empleados',
  templateUrl: './empleados.component.html',
  styleUrl: './empleados.component.scss'
})
export class EmpleadosComponent implements OnInit {
  empleados: Empleado[] = [];
  empleadosFiltrados: Empleado[] = [];
  empleadoForm!: FormGroup;
  isEdit = false;
  currentEmpleadoId?: number;
  loading = false;
  submitLoading = false;

  filtroArea = '';
  filtroTexto = '';

  constructor(
    private formBuilder: FormBuilder,
    private empleadosService: EmpleadosService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.cargarEmpleados();
  }

  initForm() {
    this.empleadoForm = this.formBuilder.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      dni: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(8)]],
      departamentoId: [1, Validators.required], // 1 por defecto (Operaciones)
      cargo: ['', Validators.required],
      activo: [true]
    });
  }

  cargarEmpleados() {
    this.loading = true;
    this.empleadosService.listar().subscribe({
      next: (data) => {
        this.empleados = data;
        this.aplicarFiltros();
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando empleados', err);
        this.loading = false;
      }
    });
  }

  aplicarFiltros() {
    this.empleadosFiltrados = this.empleados.filter(e => {
      const matchTexto = !this.filtroTexto || 
        e.nombre.toLowerCase().includes(this.filtroTexto.toLowerCase()) || 
        e.apellido.toLowerCase().includes(this.filtroTexto.toLowerCase()) ||
        e.cargo.toLowerCase().includes(this.filtroTexto.toLowerCase());
      
      const matchArea = !this.filtroArea || this.filtroArea === 'Todas las áreas' || 
        (this.filtroArea === 'Operaciones' && e.departamentoId === 1) ||
        (this.filtroArea === 'Logística' && e.departamentoId === 2) ||
        (this.filtroArea === 'SST' && e.departamentoId === 3);

      return matchTexto && matchArea;
    });
  }

  onSubmit() {
    if (this.empleadoForm.invalid) return;
    
    this.submitLoading = true;
    const empleadoData: Empleado = this.empleadoForm.value;

    if (this.isEdit && this.currentEmpleadoId) {
      this.empleadosService.actualizar(this.currentEmpleadoId, empleadoData).subscribe({
        next: () => {
          this.cargarEmpleados();
          this.resetForm();
          this.submitLoading = false;
        },
        error: (err) => {
          console.error('Error actualizando empleado', err);
          this.submitLoading = false;
        }
      });
    } else {
      this.empleadosService.crear(empleadoData).subscribe({
        next: () => {
          this.cargarEmpleados();
          this.resetForm();
          this.submitLoading = false;
        },
        error: (err) => {
          console.error('Error creando empleado', err);
          this.submitLoading = false;
        }
      });
    }
  }

  editarEmpleado(empleado: Empleado) {
    this.isEdit = true;
    this.currentEmpleadoId = empleado.id;
    this.empleadoForm.patchValue({
      nombre: empleado.nombre,
      apellido: empleado.apellido,
      dni: empleado.dni,
      departamentoId: empleado.departamentoId,
      cargo: empleado.cargo,
      activo: empleado.activo
    });
  }

  eliminarEmpleado(id?: number) {
    if (!id) return;
    if (confirm('¿Está seguro de eliminar este empleado?')) {
      this.empleadosService.eliminar(id).subscribe({
        next: () => this.cargarEmpleados(),
        error: (err) => console.error('Error eliminando empleado', err)
      });
    }
  }

  resetForm() {
    this.isEdit = false;
    this.currentEmpleadoId = undefined;
    this.empleadoForm.reset({ departamentoId: 1, activo: true });
  }
}
