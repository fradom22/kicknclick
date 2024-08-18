import {HttpErrorResponse} from '@angular/common/http';
import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LoginResponse} from 'src/app/dtos/response/LoginResponse';
import {AuthService} from 'src/app/services/auth.service';

/**
 * Componente per la gestione della pagina di login
 */
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  // Campi per la gestione del form di login
  protected username: string = '';
  protected password: string = '';
  // Messaggio di errore
  protected errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {
  }

  /**
   * Metodo per effettuare il login
   */
  login(): void {

    this.errorMessage = '';

    // Chiamata al servizio di autenticazione
    this.authService.login({username: this.username, password: this.password}).subscribe({
      next: (res: LoginResponse) => {
        // Salvataggio dei dati dell'utente nel local storage
        localStorage.setItem('utenteId', res.utenteId.toString());
        localStorage.setItem('nome', res.nome);
        localStorage.setItem('cognome', res.cognome);
        localStorage.setItem('username', res.username);
        localStorage.setItem('ruolo', res.ruolo);
        localStorage.setItem('jwt', "Bearer " + res.jwt);
        // Reindirizzamento alla homepage
        this.router.navigateByUrl('/homepage');
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
        this.errorMessage = 'Errore nel login';
      }
    });
  }

  /**
   * Metodo per reindirizzare alla pagina di registrazione
   */
  goToRegister(): void {
    this.errorMessage = '';
    this.router.navigateByUrl('/register');
  }
}
