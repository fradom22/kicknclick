import {HttpErrorResponse} from '@angular/common/http';
import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {MessageResponse} from 'src/app/dtos/response/MessageResponse';
import {AuthService} from 'src/app/services/auth.service';

/**
 * Componente per la registrazione di un nuovo utente
 */
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  protected nome: string = '';
  protected cognome: string = '';
  protected username: string = '';
  protected password: string = '';
  protected ruolo: string = '';
  protected errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {
  }

  /**
   * Registra un nuovo utente
   */
  register(): void {

    this.errorMessage = '';

    // Chiamo il servizio di autenticazione per registrare un nuovo utente
    this.authService.register(
      {nome: this.nome, cognome: this.cognome, username: this.username, password: this.password, ruolo: this.ruolo}
    ).subscribe({
      next: (res: MessageResponse) => {
        // Se la registrazione è andata a buon fine, reindirizzo alla pagina di login
        this.router.navigateByUrl('/login');
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
        this.errorMessage = 'Errore durante la registrazione';
      }
    });
  }

  /**
   * Reindirizza alla pagina di login
   */
  goToLogin(): void {
    this.router.navigateByUrl('/login');
  }
}
