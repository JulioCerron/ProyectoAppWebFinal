import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../core/services/auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent implements OnInit {
  loginForm!: FormGroup;
  loading = false;
  submitted = false;
  showPassword = false;
  errorMessage = '';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {
    const user = this.authService.currentUserValue;
    if (user) {
      this.redirectByRole(user.rol);
    }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      nombreUsuario: ['', Validators.required],
      contrasena: ['', Validators.required]
    });
  }

  get f() { return this.loginForm.controls; }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  redirectByRole(rol?: string) {
    if (rol === 'ROLE_ADMIN') this.router.navigate(['/admin']);
    else if (rol === 'ROLE_COORDINADOR') this.router.navigate(['/coordinador']);
    else if (rol === 'ROLE_SUPERVISOR') this.router.navigate(['/supervisor']);
    else if (rol === 'ROLE_AUXILIAR_LOGISTICO') this.router.navigate(['/auxiliar']);
    else if (rol === 'ROLE_SST') this.router.navigate(['/sst']);
    else {
      // Fallback or no role found
      this.authService.logout();
      this.errorMessage = 'El usuario no tiene un rol asignado válido.';
      this.loading = false;
    }
  }

  onSubmit() {
    this.submitted = true;
    this.errorMessage = '';

    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.authService.login(this.loginForm.value)
      .subscribe({
        next: (response) => {
          this.redirectByRole(response.rol);
        },
        error: (error) => {
          this.errorMessage = error.error?.message || 'Usuario o contraseña incorrectos';
          this.loading = false;
        }
      });
  }
}
