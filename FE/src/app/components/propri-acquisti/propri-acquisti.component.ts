import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {ProdottoService} from "../../services/prodotto.service";
import {ProdottoResponse} from "../../dtos/response/ProdottoResponse";
import {MessageResponse} from "../../dtos/response/MessageResponse";
import {AcquistoService} from "../../services/acquisto.service";

/**
 * Componente per la visualizzazione degli acquisti effettuati dall'utente loggato.
 */
@Component({
  selector: 'app-propri-acquisti',
  templateUrl: './propri-acquisti.component.html',
  styleUrls: ['./propri-acquisti.component.css']
})
export class PropriAcquistiComponent implements OnInit {

  // Campi utilizzati per la visualizzazione degli acquisti effettuati dall'utente loggato.
  protected scarpe: ProdottoResponse[] = [];
  protected ricercaMarca: string = '';
  protected ricercaModello: string = '';
  protected recensioneAperta: boolean = false;
  protected prodottoIdSelezionato: number = 0;
  protected testoRecensione: string = '';
  protected votoRecensione: number = 0;
  fileSelezionato = null;
  //Messaggio di errore
  protected errorMessage: string = '';

  constructor(private router: Router, private prodottoService: ProdottoService, private acquistoService: AcquistoService) {
  }

  /**
   * Metodo che viene eseguito al caricamento della pagina.
   */
  ngOnInit(): void {

    // Recupero gli acquisti effettuati dall'utente loggato.
    this.prodottoService.getByCustomer().subscribe({
      next: (result: ProdottoResponse[]) => {
        this.scarpe = result;
      },
      error: (error: any) => {
        console.error(error);
        this.errorMessage = 'Errore durante il recupero degli acquisti';
      }
    });
  }

  /**
   * Metodo per la recensione di un prodotto acquistato.
   */
  recensisci(): void {

    this.errorMessage = '';

    // Invio la richiesta di recensione del prodotto.
    this.acquistoService.recensisci(Number(localStorage.getItem('utenteId')) || 0,
      this.prodottoIdSelezionato, this.votoRecensione, this.testoRecensione, this.fileSelezionato).subscribe({
      next: (result: MessageResponse) => {
        this.recensioneAperta = false; // Chiudo la finestra di recensione.
        // Aggiorno lo stato della scarpa recensita.
        this.scarpe = this.scarpe.map(
          scarpa => scarpa.prodottoId === this.prodottoIdSelezionato ? {...scarpa, stato: 'RECENSITO'} : scarpa);
      },
      error: (error: any) => {
        console.error(error);
        this.errorMessage = 'Errore durante la recensione del prodotto';
      }
    });
  }

  /**
   * Metodo per la selezione di un file.
   * @param event evento di selezione del file.
   */
  onFileSelected(event: any) {
    this.fileSelezionato = event.target.files[0];
  }

  /**
   * Metodo per la visualizzazione della finestra di recensione di un prodotto.
   * @param id id del prodotto da recensire.
   */
  toggleRecensioneAperta(id: number): void {
    this.errorMessage = '';
    this.prodottoIdSelezionato = id;
    this.recensioneAperta = !this.recensioneAperta;
    this.votoRecensione = 0;
    this.testoRecensione = '';
    this.fileSelezionato = null;
  }

  /**
   * Metodo per resettare i filtri di ricerca.
   */
  resetFiltri(): void {
    this.errorMessage = '';
    this.ricercaMarca = '';
    this.ricercaModello = '';
  }

  /**
   * Metodo per la navigazione alla homepage.
   */
  goToHomepage(): void {
    this.router.navigateByUrl('/homepage');
  }
}
