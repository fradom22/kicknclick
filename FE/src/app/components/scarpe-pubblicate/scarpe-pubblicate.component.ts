import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {ProdottoService} from "../../services/prodotto.service";
import {HttpErrorResponse} from "@angular/common/http";
import {MessageResponse} from "../../dtos/response/MessageResponse";
import {ProdottoResponse} from "../../dtos/response/ProdottoResponse";
import {AcquistoService} from "../../services/acquisto.service";
import {RecensioneResponse} from "../../dtos/response/RecensioneResponse";

/**
 * Componente per la visualizzazione delle scarpe pubblicate dall'utente
 */
@Component({
  selector: 'app-scarpe-pubblicate',
  templateUrl: './scarpe-pubblicate.component.html',
  styleUrls: ['./scarpe-pubblicate.component.css']
})
export class ScarpePubblicateComponent implements OnInit {

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
   * Metodo che viene eseguito quando il componente viene avviato
   */
  ngOnInit(): void {

    // Recupero le scarpe pubblicate dall'utente
    this.prodottoService.getAllPubblicate(Number(localStorage.getItem('utenteId')) || 0).subscribe({
      next: (res: ProdottoResponse[]) => {
        this.scarpe = res;
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
        this.errorMessage = 'Errore nella presa delle scarpe pubblicate';
      }
    });
  }

  /**
   * Metodo per visualizzare la recensione di un prodotto
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
   * Metodo per modificare una scarpa
   * @param id id della scarpa da modificare
   */
  modifica(id: number): void {
    this.router.navigateByUrl('/pubblicaScarpa/' + id);
  }

  /**
   * Metodo per eliminare una scarpa
   * @param id id della scarpa da eliminare
   */
  elimina(id: number): void {

    this.errorMessage = '';

    //Chiamo il servizio per eliminare la scarpa
    this.prodottoService.eliminaProdotto(id).subscribe({
      next: (res: MessageResponse) => {
        //Elimino la scarpa dall'elenco di scarpe visualizzate
        this.scarpe = this.scarpe.filter(scarpa => scarpa.prodottoId !== id);
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
        this.errorMessage = 'Errore nell\'eliminazione della scarpa';
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
   * Metodo per tornare alla homepage
   */
  goToHomepage(): void {
    this.router.navigateByUrl('/homepage');
  }
}
