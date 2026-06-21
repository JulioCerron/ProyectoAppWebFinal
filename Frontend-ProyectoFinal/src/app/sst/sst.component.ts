import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../core/services/auth.service';

@Component({
  selector: 'app-sst',
  templateUrl: './sst.component.html',
  styleUrl: './sst.component.scss'
})
export class SstComponent implements OnInit {
  nombre: string = 'SST';

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    const user = this.authService.currentUserValue;
    if (user) {
      this.nombre = user.nombreUsuario || 'SST';
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
