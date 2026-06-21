import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule) },
  { path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule) },
  { path: 'coordinador', loadChildren: () => import('./coordinador/coordinador.module').then(m => m.CoordinadorModule) },
  { path: 'supervisor', loadChildren: () => import('./supervisor/supervisor.module').then(m => m.SupervisorModule) },
  { path: 'auxiliar', loadChildren: () => import('./auxiliar/auxiliar.module').then(m => m.AuxiliarModule) },
  { path: 'sst', loadChildren: () => import('./sst/sst.module').then(m => m.SstModule) }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
