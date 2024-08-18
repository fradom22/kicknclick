import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {ProdottoService} from "../../services/prodotto.service";
import {ProdottoResponse} from "../../dtos/response/ProdottoResponse";
import {HttpErrorResponse} from "@angular/common/http";

/**
 * Componente per la visualizzazione delle scarpe globali
 */
@Component({
  selector: 'app-scarpe-globali',
  templateUrl: './scarpe-globali.component.html',
  styleUrls: ['./scarpe-globali.component.css']
})
export class ScarpeGlobaliComponent implements OnInit {

  protected scarpe: ProdottoResponse[] = [];
  protected ricercaMarca: string = '';
  protected ricercaModello: string = '';
  protected errorMessage: string = '';

  constructor(private router: Router, private prodottoService: ProdottoService) {
  }

  /**
   * Metodo che viene chiamato quando il componente viene inizializzato
   */
  ngOnInit(): void {

    // Prendo tutte le scarpe non vendute
    this.prodottoService.getAllNonVendute().subscribe({
      next: (res: ProdottoResponse[]) => {
        this.scarpe = res;
      },
      error: (err: HttpErrorResponse) => {
        this.errorMessage = 'Errore nella presa degli utenti';
        console.log(err);
      }
    })
  }

  /**
   * Metodo per resettare i filtri
   */
  resetFiltri(): void {
    this.errorMessage = '';
    this.ricercaMarca = '';
    this.ricercaModello = '';
  }

  /**
   * Metodo per andare alla pagina di dettagli di una scarpa
   * @param scarpaId id della scarpa
   */
  goToDettagliScarpa(scarpaId: number): void {
    this.router.navigateByUrl('/dettagliScarpa/' + scarpaId);
  }

  /**
   * Metodo per andare alla homepage
   */
  goToHomepage(): void {
    this.router.navigateByUrl('/homepage');
  }
}
