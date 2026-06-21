import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { CoordinadorRoutingModule } from './coordinador-routing.module';
import { CoordinadorComponent } from './coordinador.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ListaIncidenciasComponent } from './lista-incidencias/lista-incidencias.component';
import { DetalleIncidenciaComponent } from './detalle-incidencia/detalle-incidencia.component';


@NgModule({
  declarations: [
    CoordinadorComponent,
    DashboardComponent,
    ListaIncidenciasComponent,
    DetalleIncidenciaComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    CoordinadorRoutingModule
  ]
})
export class CoordinadorModule { }
