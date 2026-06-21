import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IncidenciasService } from '../../core/services/incidencias.service';

@Component({
  selector: 'app-detalle-incidencia',
  templateUrl: './detalle-incidencia.component.html'
})
export class DetalleIncidenciaComponent implements OnInit {
  incidencia: any;
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private incidenciasService: IncidenciasService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.cargarDetalle(+id);
    } else {
      this.router.navigate(['/supervisor/incidencias']);
    }
  }

  cargarDetalle(id: number) {
    this.loading = true;
    this.incidenciasService.obtenerPorId(id).subscribe({
      next: (data) => {
        this.incidencia = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar la incidencia', err);
        this.router.navigate(['/supervisor/incidencias']);
      }
    });
  }
}
