import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../core/services/auth.service';

@Component({
  selector: 'app-coordinador',
  templateUrl: './coordinador.component.html',
  styleUrl: './coordinador.component.scss'
})
export class CoordinadorComponent implements OnInit {
  nombre: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    const user = this.authService.currentUserValue;
    if (user) {
      this.nombre = user.nombreUsuario || 'Coordinador';
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
