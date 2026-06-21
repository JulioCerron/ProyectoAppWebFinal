import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuxiliarRoutingModule } from './auxiliar-routing.module';
import { AuxiliarComponent } from './auxiliar.component';
import { InventarioComponent } from './inventario/inventario.component';
import { SolicitudesComponent } from './solicitudes/solicitudes.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AuxiliarComponent,
    InventarioComponent,
    SolicitudesComponent
  ],
  imports: [
    CommonModule,
    AuxiliarRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class AuxiliarModule { }
