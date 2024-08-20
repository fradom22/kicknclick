import {Component, OnInit} from '@angular/core';
import {ProdottoService} from "../../services/prodotto.service";
import {MessageResponse} from "../../dtos/response/MessageResponse";
import {HttpErrorResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import { ProdottoResponse } from 'src/app/dtos/response/ProdottoResponse';

/**
 * Componente per la pubblicazione di una scarpa
 */
@Component({
  selector: 'app-pubblica-scarpa',
  templateUrl: './pubblica-scarpa.component.html',
  styleUrls: ['./pubblica-scarpa.component.css']
})
export class PubblicaScarpaComponent implements OnInit {

  protected marca: string = '';
  protected modello: string = '';
  protected prezzo: number = 0;
  protected misura: number = 0;
  fileSelezionato = null;
  protected idProdotto: number = +this.router.url.split('/')[2];

  protected errorMessage: string = '';

  constructor(private prodottoService: ProdottoService, private router: Router) {
  }

  /**
   * Metodo per recuperare i dettagli di una scarpa
   */
  ngOnInit(): void {
    //Se non è presente l'id del prodotto nel path, allora viene visualizzata la pagina per la pubblicazione di una nuova scarpa
    if (!this.idProdotto) {
      return;
    }
    //Se è presente l'id del prodotto nel path, allora vengono visualizzati i dettagli della scarpa ed eventualmente modificarli
    this.prodottoService.dettagliScarpa(this.idProdotto).subscribe({
      next: (res: ProdottoResponse) => {
        this.marca = res.marca;
        this.modello = res.modello;
        this.prezzo = res.prezzo;
        this.misura = res.misura;
      }, error: (error: HttpErrorResponse) => {
        console.log(error);
        this.errorMessage = 'Errore durante il recupero della scarpa';
      }
    })
  }

  /**
   * Metodo per pubblicare una scarpa
   */
  pubblica(): void {

    this.errorMessage = '';

    //Chiamo il servizio per pubblicare la scarpa
    this.prodottoService.pubblica(this.marca, this.modello, this.prezzo, this.misura, this.fileSelezionato).subscribe({
      next: (res: MessageResponse) => {
        //Se la pubblicazione è andata a buon fine, allora viene visualizzata la homepage
        this.router.navigateByUrl('/homepage');
      }, error: (error: HttpErrorResponse) => {
        console.log(error);
        this.errorMessage = 'Errore durante la pubblicazione della scarpa';
      }
    })
  }

  /**
   * Metodo per modificare una scarpa
   */
  modifica(): void {

    this.errorMessage = '';

    //Chiamo il servizio per modificare la scarpa
    this.prodottoService.modifica(this.marca, this.modello, this.prezzo, this.misura, this.idProdotto).subscribe({
      next: (res: MessageResponse) => {
        //Se la modifica è andata a buon fine, allora viene visualizzata la homepage
        this.router.navigateByUrl('/homepage');
      }, error: (error: HttpErrorResponse) => {
        console.log(error);
        this.errorMessage = 'Errore durante la modifica della scarpa';
      }
    })
  }

  /**
   * Metodo per selezionare un file
   * @param event evento di selezione del file
   */
  onFileSelected(event: any) {
    this.fileSelezionato = event.target.files[0];
  }

  /**
   * Metodo per tornare alla homepage
   */
  goToHomepage(): void {
    this.router.navigateByUrl('/homepage');
  }
}
