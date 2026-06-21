import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { SupervisorRoutingModule } from './supervisor-routing.module';
import { SupervisorComponent } from './supervisor.component';
import { RegistrarIncidenciaComponent } from './registrar-incidencia/registrar-incidencia.component';
import { MisIncidenciasComponent } from './mis-incidencias/mis-incidencias.component';
import { DetalleIncidenciaComponent } from './detalle-incidencia/detalle-incidencia.component';

@NgModule({
  declarations: [
    SupervisorComponent,
    RegistrarIncidenciaComponent,
    MisIncidenciasComponent,
    DetalleIncidenciaComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    SupervisorRoutingModule
  ]
})
export class SupervisorModule { }
