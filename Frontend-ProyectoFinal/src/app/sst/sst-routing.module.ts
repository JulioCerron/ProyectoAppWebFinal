import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SstComponent } from './sst.component';

import { EvaluacionesComponent } from './evaluaciones/evaluaciones.component';
import { DetalleEvaluacionComponent } from './detalle-evaluacion/detalle-evaluacion.component';

const routes: Routes = [
  { 
    path: '', 
    component: SstComponent,
    children: [
      { path: 'evaluaciones', component: EvaluacionesComponent },
      { path: 'evaluaciones/:id', component: DetalleEvaluacionComponent },
      { path: '', redirectTo: 'evaluaciones', pathMatch: 'full' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SstRoutingModule { }
