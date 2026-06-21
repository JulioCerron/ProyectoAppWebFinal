import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.scss'
})
export class UsuariosComponent implements OnInit {
  defaultUsuarios = [
    { nombreUsuario: 'admin', correo: 'admin@handfast.com', rolNombre: 'Administrador', activo: true },
    { nombreUsuario: 'coord', correo: 'coord@handfast.com', rolNombre: 'Coordinador', activo: true },
    { nombreUsuario: 'supervisor', correo: 'super@handfast.com', rolNombre: 'Supervisor', activo: true },
    { nombreUsuario: 'auxiliar', correo: 'auxiliar@handfast.com', rolNombre: 'Auxiliar Logístico', activo: false },
    { nombreUsuario: 'sst', correo: 'sst@handfast.com', rolNombre: 'SST', activo: true }
  ];

  usuarios: any[] = [];
  usuariosFiltrados: any[] = [];
  userForm!: FormGroup;
  roleForm!: FormGroup;
  
  filtroTexto = '';
  filtroRol = '';
  filtroEstado = '';

  loading = false;
  successMessage = '';
  errorMessage = '';
  selectedUser: any = null;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.userForm = this.formBuilder.group({
      nombreUsuario: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]],
      contrasena: ['', Validators.required],
      activo: [true]
    });

    this.roleForm = this.formBuilder.group({
      rolNombre: ['', Validators.required]
    });

    this.cargarUsuarios();
  }

  cargarUsuarios() {
    const localUsers = localStorage.getItem('mockedUsers');
    if (localUsers) {
      this.usuarios = JSON.parse(localUsers);
    } else {
      this.usuarios = [...this.defaultUsuarios];
      this.guardarLocal();
    }
    this.aplicarFiltros();
  }

  guardarLocal() {
    localStorage.setItem('mockedUsers', JSON.stringify(this.usuarios));
    this.aplicarFiltros();
  }

  aplicarFiltros() {
    this.usuariosFiltrados = this.usuarios.filter(u => {
      const matchTexto = !this.filtroTexto || 
        u.nombreUsuario.toLowerCase().includes(this.filtroTexto.toLowerCase()) || 
        u.correo.toLowerCase().includes(this.filtroTexto.toLowerCase());
      
      const matchRol = !this.filtroRol || this.filtroRol === 'Todos los roles' || u.rolNombre === this.filtroRol;

      let matchEstado = true;
      if (this.filtroEstado === 'Activo') matchEstado = u.activo === true;
      if (this.filtroEstado === 'Inactivo') matchEstado = u.activo === false;

      return matchTexto && matchRol && matchEstado;
    });
  }

  getActivos(): number {
    return this.usuarios.filter(u => u.activo).length;
  }

  registrarUsuario() {
    if (this.userForm.invalid) return;

    this.loading = true;
    this.successMessage = '';
    this.errorMessage = '';

    const payload = this.userForm.value;

    this.authService.registrar(payload).subscribe({
      next: (res) => {
        this.loading = false;
        this.successMessage = 'Usuario registrado en la Base de Datos exitosamente.';
        
        this.usuarios.unshift({
          nombreUsuario: payload.nombreUsuario,
          correo: payload.correo,
          rolNombre: 'Supervisor', 
          activo: payload.activo
        });
        this.guardarLocal();

        this.userForm.reset({ activo: true });
        setTimeout(() => this.successMessage = '', 3000);
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage = 'Hubo un error al registrar el usuario.';
      }
    });
  }

  abrirEditar(usuario: any) {
    this.selectedUser = usuario;
    this.userForm.patchValue({
      nombreUsuario: usuario.nombreUsuario,
      correo: usuario.correo,
      contrasena: '',
      activo: usuario.activo
    });
  }

  actualizarUsuario() {
    if (this.userForm.invalid) return;
    
    // Solo actualiza visualmente ya que MS-AUTH no tiene endpoint de PUT
    const formVal = this.userForm.value;
    const index = this.usuarios.findIndex(u => u.nombreUsuario === this.selectedUser.nombreUsuario);
    if (index !== -1) {
      this.usuarios[index].nombreUsuario = formVal.nombreUsuario;
      this.usuarios[index].correo = formVal.correo;
      this.usuarios[index].activo = formVal.activo;
      this.guardarLocal();
    }

    this.successMessage = 'Usuario actualizado visualmente (Backend no soporta PUT).';
    setTimeout(() => this.successMessage = '', 3000);
  }

  abrirRol(usuario: any) {
    this.selectedUser = usuario;
    this.roleForm.patchValue({
      rolNombre: usuario.rolNombre
    });
  }

  actualizarRol() {
    if (this.roleForm.invalid) return;

    const formVal = this.roleForm.value;
    const index = this.usuarios.findIndex(u => u.nombreUsuario === this.selectedUser.nombreUsuario);
    if (index !== -1) {
      this.usuarios[index].rolNombre = formVal.rolNombre;
      this.guardarLocal();
    }
  }

  prepararNuevo() {
    this.selectedUser = null;
    this.userForm.reset({ activo: true });
  }
}
