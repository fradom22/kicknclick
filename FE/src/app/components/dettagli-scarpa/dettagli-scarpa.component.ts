import {Component, OnInit} from '@angular/core';
import {ProdottoService} from "../../services/prodotto.service";
import {Router} from "@angular/router";
import {ProdottoResponse} from "../../dtos/response/ProdottoResponse";
import {HttpErrorResponse} from "@angular/common/http";
import {MessageResponse} from "../../dtos/response/MessageResponse";
import {AcquistoService} from "../../services/acquisto.service";

/**
 * Componente per la visualizzazione dei dettagli di una scarpa.
 */
@Component({
  selector: 'app-dettagli-scarpa',
  templateUrl: './dettagli-scarpa.component.html',
  styleUrls: ['./dettagli-scarpa.component.css']
})
export class DettagliScarpaComponent implements OnInit {

  //Messaggio di errore
  protected errorMessage: string = '';
  //Oggetto scarpa da visualizzare
  protected scarpa: ProdottoResponse = {} as ProdottoResponse;

  constructor(private router: Router, private prodottoService: ProdottoService, private acquistoService: AcquistoService) {
  }

  /**
   * Metodo che viene eseguito alla creazione del componente.
   */
  ngOnInit(): void {

    //Prendo l'id della scarpa dall'url
    const scarpaId: number = +this.router.url.split('/')[2] || 0;

    //Prendo i dettagli della scarpa con l'id preso
    this.prodottoService.dettagliScarpa(scarpaId).subscribe({
      next: (response: ProdottoResponse) => {
        //Assegno i dati all'oggetto
        this.scarpa = response;
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
        this.errorMessage = 'Errore nella presa della scarpa.';
      }
    });
  }

  /**
   * Metodo per l'acquisto di una scarpa.
   */
  acquista(): void {

    //Chiamo il servizio per l'acquisto della scarpa, passando l'id della scarpa
    this.acquistoService.acquista(this.scarpa.prodottoId).subscribe({
      next: (res: MessageResponse) => {
        //Completato l'acquisto, reindirizzo alla pagina delle scarpe globali
        this.router.navigateByUrl('/scarpeGlobali');
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
        this.errorMessage = 'Errore nell\'acquisto della scarpa.';
      }
    });
  }

  /**
   * Metodo per tornare alla homepage.
   */
  goToHomepage(): void {
    this.router.navigateByUrl('/homepage');
  }
}
