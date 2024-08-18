import { Injectable } from '@angular/core';
import { globalBackendUrl } from '../utils/environment';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginResponse } from '../dtos/response/LoginResponse';
import { LoginRequest } from '../dtos/request/LoginRequest';
import { RegisterRequest } from '../dtos/request/RegisterRequest';
import { MessageResponse } from '../dtos/response/MessageResponse';

/**
 * Service per l'autenticazione dell'utente
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // URL del backend per l'AuthController
  private backendUrl: string = globalBackendUrl + 'auth/';

  constructor(private http: HttpClient, private router: Router) { }

  /**
   * Effettua il login dell'utente
   * @param request dati di login
   */
  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.backendUrl + 'login', request);
  }

  /**
   * Registra un nuovo utente
   * @param request dati di registrazione
   */
  register(request: RegisterRequest): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(this.backendUrl + 'register', request);
  }

  /**
   * Effettua il logout dell'utente
   * Cancella i dati dell'utente dal localStorage e reindirizza alla pagina di login
   */
  logout(): void {
    this.router.navigateByUrl('login');
    localStorage.clear();
  }
}
