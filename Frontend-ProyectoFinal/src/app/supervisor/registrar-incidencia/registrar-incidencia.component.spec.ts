import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrarIncidenciaComponent } from './registrar-incidencia.component';

describe('RegistrarIncidenciaComponent', () => {
  let component: RegistrarIncidenciaComponent;
  let fixture: ComponentFixture<RegistrarIncidenciaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegistrarIncidenciaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RegistrarIncidenciaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
