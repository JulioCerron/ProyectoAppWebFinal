import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AuthResponse, LoginRequest } from '../models/auth.models';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;
  private currentUserSubject = new BehaviorSubject<AuthResponse | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    const storedUser = localStorage.getItem('currentUser');
    if (storedUser) {
      this.currentUserSubject.next(JSON.parse(storedUser));
    }
  }

  login(credenciales: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, credenciales).pipe(
      tap(response => {
        if (response && response.token) {
          try {
            const payload = JSON.parse(atob(response.token.split('.')[1]));
            if (payload.roles && payload.roles.length > 0) {
              response.rol = payload.roles[0];
            } else {
              // Workaround: Inferir el rol desde el nombre de usuario ya que el backend no lo expone en el JWT
              const username = (payload.sub || '').toLowerCase();
              if (username.includes('admin')) { response.rol = 'ROLE_ADMIN'; response.id = 1; }
              else if (username.includes('coord')) { response.rol = 'ROLE_COORDINADOR'; response.id = 2; }
              else if (username.includes('super')) { response.rol = 'ROLE_SUPERVISOR'; response.id = 3; }
              else if (username.includes('auxiliar') || username.includes('logist')) { response.rol = 'ROLE_AUXILIAR_LOGISTICO'; response.id = 4; }
              else if (username.includes('sst')) { response.rol = 'ROLE_SST'; response.id = 5; }
              else { response.rol = 'ROLE_SUPERVISOR'; response.id = 3; } // Fallback por defecto (basado en AutenticacionUseCase)
            }
          } catch (e) {
            console.error('Error decoding JWT', e);
          }
          localStorage.setItem('currentUser', JSON.stringify(response));
          this.currentUserSubject.next(response);
        }
      })
    );
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  public get currentUserValue(): AuthResponse | null {
    return this.currentUserSubject.value;
  }

  public getToken(): string | null {
    const user = this.currentUserValue;
    return user ? user.token : null;
  }

  registrar(usuario: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/registro`, usuario);
  }
}
