import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {globalBackendUrl} from "../utils/environment";
import {Observable} from "rxjs";
import {MessageResponse} from "../dtos/response/MessageResponse";
import {RecensioneResponse} from "../dtos/response/RecensioneResponse";
import {AcquistaRequest} from "../dtos/request/AcquistaRequest";

/**
 * Service per la gestione degli acquisti
 */
@Injectable({
  providedIn: 'root'
})
export class AcquistoService {

  //Url che porta all'AcquistoController del backend
  private backendUrl: string = globalBackendUrl + 'acquisto/';

  constructor(private http: HttpClient) {
  }

  /**
   * Metodo che permette di acquistare una scarpa
   * @param scarpaId id della scarpa da acquistare
   */
  acquista(scarpaId: number): Observable<MessageResponse> {
    const request: AcquistaRequest = {scarpaId: +scarpaId, utenteId: Number(localStorage.getItem('utenteId')) || 0};
    return this.http.post<MessageResponse>(this.backendUrl + 'acquista/', request, {headers: this.getHeader()});
  }

  /**
   * Metodo che permette di recensire un prodotto
   * @param customerId id del cliente che recensisce
   * @param prodottoId id del prodotto da recensire
   * @param votoRecensione voto assegnato alla recensione
   * @param testoRecensione testo della recensione
   * @param fileSelezionato immagine della recensione
   */
  recensisci(customerId: number, prodottoId: number, votoRecensione: number, testoRecensione: string, fileSelezionato: any): Observable<MessageResponse> {
    //Creo un formdata e appendo i dati necessari, tra cui l'immagine
    const formData: FormData = new FormData();
    formData.append("customerId", customerId.toString());
    formData.append("prodottoId", prodottoId.toString());
    formData.append("votoRecensione", votoRecensione.toString());
    formData.append("testoRecensione", testoRecensione);
    formData.append("immagine", fileSelezionato);
    return this.http.post<MessageResponse>(this.backendUrl + 'recensione/', formData, {headers: this.getHeader()});
  }

  /**
   * Metodo che permette di visualizzare la recensione di un prodotto
   * @param prodottoId id del prodotto di cui si vuole visualizzare la recensione
   */
  visualizzaRecensione(prodottoId: number): Observable<RecensioneResponse> {
    return this.http.get<RecensioneResponse>(this.backendUrl + 'visualizzaRecensione/' + prodottoId, {headers: this.getHeader()});
  }

  /**
   * Metodo che ritorna l'header per le richieste HTTP con il token dell'utente
   */
  private getHeader(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': localStorage.getItem('jwt') ? `${localStorage.getItem('jwt')}` : '',
      id: localStorage.getItem('utenteId') ? `${localStorage.getItem('utenteId')}` : '',
      ruolo: localStorage.getItem('ruolo') ? `${localStorage.getItem('ruolo')}` : ''
    });
  }
}
