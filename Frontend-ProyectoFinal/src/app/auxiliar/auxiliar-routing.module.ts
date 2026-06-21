import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuxiliarComponent } from './auxiliar.component';

import { InventarioComponent } from './inventario/inventario.component';
import { SolicitudesComponent } from './solicitudes/solicitudes.component';

const routes: Routes = [
  { 
    path: '', 
    component: AuxiliarComponent,
    children: [
      { path: 'inventario', component: InventarioComponent },
      { path: 'solicitudes', component: SolicitudesComponent },
      { path: '', redirectTo: 'inventario', pathMatch: 'full' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuxiliarRoutingModule { }
