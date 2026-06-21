import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CoordinadorComponent } from './coordinador.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ListaIncidenciasComponent } from './lista-incidencias/lista-incidencias.component';
import { DetalleIncidenciaComponent } from './detalle-incidencia/detalle-incidencia.component';

const routes: Routes = [
  { 
    path: '', 
    component: CoordinadorComponent,
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'incidencias', component: ListaIncidenciasComponent },
      { path: 'incidencias/:id', component: DetalleIncidenciaComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CoordinadorRoutingModule { }
