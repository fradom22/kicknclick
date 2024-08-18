import {HttpErrorResponse} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UtentiGlobaliResponse} from 'src/app/dtos/response/UtentiGlobaliResponse';
import {UtenteService} from 'src/app/services/utente.service';

/**
 * Componente per la visualizzazione di tutti gli utenti globali
 */
@Component({
  selector: 'app-utenti-globali',
  templateUrl: './utenti-globali.component.html',
  styleUrls: ['./utenti-globali.component.css']
})
export class UtentiGlobaliComponent implements OnInit {

  protected utenti: UtentiGlobaliResponse[] = [];
  protected ricercaNome: string = '';
  protected ricercaCognome: string = '';
  protected ricercaRuolo: string = '';
  protected errorMessage: string = '';

  constructor(private router: Router, private utenteService: UtenteService) {
  }

  /**
   * Metodo eseguito alla creazione del componente
   */
  ngOnInit(): void {

    // Prendo tutti gli utenti globali
    this.utenteService.getAll().subscribe({
      next: (result: UtentiGlobaliResponse[]) => {
        this.utenti = result;
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
        this.errorMessage = 'Errore durante la presa di tutti gli utenti';
      }
    });
  }

  /**
   * Metodo per la modifica di un utente
   * @param utenteId id dell'utente da modificare
   */
  goToModificaUtente(utenteId: number): void {
    this.router.navigateByUrl('/modifica/' + utenteId);
  }

  /**
   * Metodo per resettare i filtri di ricerca
   */
  resetFiltri(): void {
    this.errorMessage = '';
    this.ricercaNome = '';
    this.ricercaCognome = '';
    this.ricercaRuolo = '';
  }

  /**
   * Metodo per navigare alla homepage
   */
  goToHomepage(): void {
    this.router.navigateByUrl('/homepage');
  }
}
