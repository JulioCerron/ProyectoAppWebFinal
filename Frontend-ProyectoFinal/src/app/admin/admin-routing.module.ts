import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin.component';

import { UsuariosComponent } from './usuarios/usuarios.component';
import { EmpleadosComponent } from './empleados/empleados.component';

const routes: Routes = [{ 
  path: '', 
  component: AdminComponent,
  children: [
    { path: '', redirectTo: 'empleados', pathMatch: 'full' },
    { path: 'empleados', component: EmpleadosComponent },
    { path: 'usuarios', component: UsuariosComponent }
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
