import {HttpErrorResponse} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {MessageResponse} from 'src/app/dtos/response/MessageResponse';
import {UtenteResponse} from 'src/app/dtos/response/UtenteResponse';
import {UtenteService} from 'src/app/services/utente.service';

/**
 * Componente per la modifica dell'utente
 */
@Component({
  selector: 'app-modifica-utente',
  templateUrl: './modifica-utente.component.html',
  styleUrls: ['./modifica-utente.component.css']
})
export class ModificaUtenteComponent implements OnInit {

  // Attributi dell'utente da modificare
  protected username: string = '';
  protected nome: string = '';
  protected cognome: string = '';
  protected vecchiaPassword: string = '';
  protected nuovaPassword: string = '';
  // Messaggio di errore
  protected errorMessage: string = '';

  constructor(private router: Router, private utenteService: UtenteService) {
  }

  /**
   * Metodo eseguito al caricamento del componente
   */
  ngOnInit(): void {

    // Prendo i dati dell'utente con l'id passato nell'url
    this.utenteService.get(this.router.url.split('/')[2]).subscribe({
      next: (result: UtenteResponse) => {
        // Inizializzo gli attributi dell'utente, così da visualizzarli nel form
        this.username = result.username;
        this.nome = result.nome;
        this.cognome = result.cognome;
      },
      error: (error) => {
        console.log(error);
        this.errorMessage = 'Errore durante la presa dei dati';
      }
    });
  }

  /**
   * Metodo per la modifica dell'utente
   */
  modifica(): void {

    this.errorMessage = '';

    // Chiamo il servizio per la modifica dell'utente
    this.utenteService.modifica(
      {
        id: Number(this.router.url.split('/')[2]),
        username: this.username,
        nome: this.nome,
        cognome: this.cognome,
        vecchiaPassword: this.vecchiaPassword,
        nuovaPassword: this.nuovaPassword
      }
    ).subscribe({
      next: (result: MessageResponse) => {
        // Se la modifica è andata a buon fine, reindirizzo l'utente alla pagina di login
        this.router.navigateByUrl('/login');
      },
      error: (error) => {
        console.log(error);
        this.errorMessage = 'Errore durante la modifica dell\'utente';
      }
    });
  }

  /**
   * Metodo per l'eliminazione dell'utente
   */
  elimina(): void {
    this.errorMessage = '';

    // Chiamo il servizio per l'eliminazione dell'utente
    this.utenteService.elimina(this.router.url.split('/')[2]).subscribe({
      next: (result: MessageResponse) => {
        // Se l'eliminazione è andata a buon fine, reindirizzo l'utente alla pagina di login
        this.router.navigateByUrl('/login');
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
        this.errorMessage = 'Errore durante l\'eliminazione dell\'utente';
      }
    });
  }

  /**
   * Metodo per tornare alla homepage
   */
  goToHomepage(): void {
    this.router.navigateByUrl('/homepage');
  }
}
