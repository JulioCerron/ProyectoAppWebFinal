import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SupervisorComponent } from './supervisor.component';
import { RegistrarIncidenciaComponent } from './registrar-incidencia/registrar-incidencia.component';

import { MisIncidenciasComponent } from './mis-incidencias/mis-incidencias.component';
import { DetalleIncidenciaComponent } from './detalle-incidencia/detalle-incidencia.component';

const routes: Routes = [
  { 
    path: '', 
    component: SupervisorComponent,
    children: [
      { path: 'registrar', component: RegistrarIncidenciaComponent },
      { path: 'incidencias', component: MisIncidenciasComponent },
      { path: 'incidencias/:id', component: DetalleIncidenciaComponent },
      { path: '', redirectTo: 'incidencias', pathMatch: 'full' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SupervisorRoutingModule { }
