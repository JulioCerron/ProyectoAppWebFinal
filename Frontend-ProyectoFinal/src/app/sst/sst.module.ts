import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SstRoutingModule } from './sst-routing.module';
import { SstComponent } from './sst.component';
import { EvaluacionesComponent } from './evaluaciones/evaluaciones.component';
import { DetalleEvaluacionComponent } from './detalle-evaluacion/detalle-evaluacion.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    SstComponent,
    EvaluacionesComponent,
    DetalleEvaluacionComponent
  ],
  imports: [
    CommonModule,
    SstRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class SstModule { }
