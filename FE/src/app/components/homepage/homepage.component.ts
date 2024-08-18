import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from "../../services/auth.service";

/**
 * Componente per la visualizzazione della homepage
 * Contiene i metodi per il reindirizzamento alle pagine
 */
@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent {

  //Nome dell'utente in sessione
  protected nome: string = localStorage.getItem('nome') || '';
  //Ruolo dell'utente in sessione
  protected ruolo: string = localStorage.getItem('ruolo') || '';

  constructor(private router: Router, private authService: AuthService) {
  }

  /**
   * Metodo per il reindirizzamento alla pagina di modifica utente
   */
  goToModificaUtente(): void {
    this.router.navigateByUrl('/modifica/' + localStorage.getItem('utenteId') || '');
  }

  /**
   * Metodo per il reindirizzamento alla pagina delle sacrpe globali
   */
  goToScarpeGlobali(): void {
    this.router.navigateByUrl('/scarpeGlobali');
  }

  /**
   * Metodo per il reindirizzamento alla pagina degli utenti globali
   */
  goToUtentiGlobali(): void {
    this.router.navigateByUrl('/utentiGlobali');
  }

  /**
   * Metodo per il reindirizzamento alla pagina di tutti i prodotti
   */
  goToTuttiProdotti(): void {
    this.router.navigateByUrl('/tuttiProdotti');
  }

  /**
   * Metodo per il reindirizzamento alla pagina di pubblicazione di una scarpa
   */
  goToPubblicaScarpa(): void {
    this.router.navigateByUrl('/pubblicaScarpa');
  }

  /**
   * Metodo per il reindirizzamento alla pagina delle scarpe pubblicate
   */
  goToScarpePubblicate(): void {
    this.router.navigateByUrl('/scarpePubblicate');
  }

  /**
   * Metodo per il reindirizzamento alla pagina degli acquisti effettuati
   */
  goToPropriAcquisti(): void {
    if (localStorage.getItem('utenteId')) {
      this.router.navigateByUrl('/propriAcquisti/' + localStorage.getItem('utenteId'));
    }
  }

  /**
   * Metodo per il logout dell'utente
   * Cancella i dati dell'utente dalla sessione e reindirizza alla pagina di login
   */
  logout(): void {
    this.authService.logout();
  }
}
