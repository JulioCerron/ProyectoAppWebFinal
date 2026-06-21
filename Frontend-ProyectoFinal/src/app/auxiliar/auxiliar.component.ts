import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../core/services/auth.service';

@Component({
  selector: 'app-auxiliar',
  templateUrl: './auxiliar.component.html',
  styleUrl: './auxiliar.component.scss'
})
export class AuxiliarComponent implements OnInit {
  nombre: string = 'Auxiliar';

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    const user = this.authService.currentUserValue;
    if (user) {
      this.nombre = user.nombreUsuario || 'Auxiliar';
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
