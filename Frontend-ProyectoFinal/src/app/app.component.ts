import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { AuthService } from './core/services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'Frontend-ProyectoFinal';
  isLoggedIn = false;
  nombre = '';
  rol = '';
  isLoginPage = false;

  constructor(private authService: AuthService, private router: Router) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.isLoginPage = event.urlAfterRedirects.includes('/login');
      }
    });
  }

  ngOnInit(): void {
    this.authService.currentUser$.subscribe(user => {
      this.isLoggedIn = !!user;
      if (user) {
        this.nombre = user.nombreUsuario || 'Usuario';
        this.rol = user.rol || '';
      } else {
        this.nombre = '';
        this.rol = '';
      }
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  get isAdmin() { return this.rol === 'ROLE_ADMIN'; }
  get isCoordinador() { return this.rol === 'ROLE_COORDINADOR'; }
  get isSupervisor() { return this.rol === 'ROLE_SUPERVISOR'; }
  get isSst() { return this.rol === 'ROLE_SST'; }
  get isLogistica() { return this.rol === 'ROLE_AUXILIAR_LOGISTICO'; }
}
