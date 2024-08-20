import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {ProdottoService} from "../../services/prodotto.service";
import {ProdottoResponse} from "../../dtos/response/ProdottoResponse";
import {RecensioneResponse} from "../../dtos/response/RecensioneResponse";
import {HttpErrorResponse} from "@angular/common/http";
import {MessageResponse} from "../../dtos/response/MessageResponse";
import {AcquistoService} from "../../services/acquisto.service";

/**
 * Componente per la visualizzazione di tutti i prodotti presenti nel database
 */
@Component({
  selector: 'app-tutti-prodotti',
  templateUrl: './tutti-prodotti.component.html',
  styleUrls: ['./tutti-prodotti.component.css']
})
export class TuttiProdottiComponent implements OnInit {

  protected scarpe: ProdottoResponse[] = [];
  protected ricercaMarca: string = '';
  protected ricercaModello: string = '';
  protected ricercaStato: string = '';
  protected testoRecensione: string = '';
  protected votoRecensione: number = 0;
  protected viewRecensione: boolean = false;
  protected immagine: any;
  protected prodottoIdSelezionato: number = 0;
  protected errorMessage: string = '';

  constructor(private router: Router, private prodottoService: ProdottoService, private acquistoService: AcquistoService) {
  }

  /**
   * Meotodo eseguito al caricamento della pagina
   */
  ngOnInit(): void {

    // Recupero tutti i prodotti presenti nel database
    this.prodottoService.getAll().subscribe({
      next: (result: ProdottoResponse[]) => {
        this.scarpe = result;
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
        this.errorMessage = 'Errore nella ricerca dei prodotti';
      }
    });
  }

  /**
   * Metodo per la visualizzazione della recensione di un prodotto
   * @param prodottoId id del prodotto di cui visualizzare la recensione
   */
  visualizzaRecensione(prodottoId: number): void {

    this.errorMessage = '';

    // Recupero la recensione del prodotto
    this.acquistoService.visualizzaRecensione(prodottoId).subscribe({
      next: (res: RecensioneResponse) => {
        //Se la recensione è già visualizzata, la nascondo
        if (this.prodottoIdSelezionato === prodottoId) {
          this.viewRecensione = false;
          this.prodottoIdSelezionato = 0;
        } else { //Altrimenti la visualizzo
          this.viewRecensione = true;
          this.prodottoIdSelezionato = prodottoId;
        } //In entrambi i casi setto voto, testo e immagine
        this.testoRecensione = res.testoRecensione;
        this.votoRecensione = res.votoRecensione;
        this.immagine = res.immagine;
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
        this.errorMessage = 'Errore nella visualizzazione della recensione';
      }
    });
  }

  /**
   * Metodo per la modifica di un prodotto
   * @param id id del prodotto da modificare
   */
  modifica(id: number): void {
    this.router.navigateByUrl('/pubblicaScarpa/' + id);
  }

  /**
   * Metodo per l'eliminazione di un prodotto
   * @param id id del prodotto da eliminare
   */
  elimina(id: number): void {

    this.errorMessage = '';

    // Elimino il prodotto dal database
    this.prodottoService.eliminaProdotto(id).subscribe({
      next: (res: MessageResponse) => {
        //Elimino il prodotto dalla lista visualizzata
        this.scarpe = this.scarpe.filter(scarpa => scarpa.prodottoId !== id);
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
        this.errorMessage = 'Errore nell\'eliminazione del prodotto';
      }
    });
  }

  /**
   * Metodo per resettare i filtri di ricerca
   */
  resetFiltri(): void {
    this.errorMessage = '';
    this.ricercaMarca = '';
    this.ricercaModello = '';
    this.ricercaStato = '';
  }

  /**
   * Metodo per la navigazione alla homepage
   */
  goToHomepage(): void {
    this.router.navigateByUrl('/homepage');
  }
}
