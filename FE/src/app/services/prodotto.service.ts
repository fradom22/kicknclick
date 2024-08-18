import {Injectable} from '@angular/core';
import {globalBackendUrl} from "../utils/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {MessageResponse} from "../dtos/response/MessageResponse";
import {ProdottoResponse} from "../dtos/response/ProdottoResponse";
import {ModificaProdottoRequest} from "../dtos/request/ModificaProdottoRequest";

/**
 * Classe service contenente i metodi per effettuare richieste al backend per le operazioni sui prodotti
 */
@Injectable({
  providedIn: 'root'
})
export class ProdottoService {

  // Url del backend per il ProdottoController
  private backendUrl: string = globalBackendUrl + 'prodotto/';

  constructor(private http: HttpClient) {
  }

  /**
   * Metodo per pubblicare un prodotto
   * @param marca marca della scarpa
   * @param modello modello della scarpa
   * @param prezzo prezzo della scarpa
   * @param misura misura della scarpa
   * @param fileSelezionato file dell'immagine della scarpa
   */
  pubblica(marca: string, modello: string, prezzo: number, misura: number, fileSelezionato: any): Observable<MessageResponse> {
    //Creo un oggetto FormData per inviare i dati del prodotto, compresa l'immagine
    const formData: FormData = new FormData();
    formData.append("marca", marca);
    formData.append("modello", modello);
    formData.append("prezzo", prezzo.toString());
    formData.append("misura", misura.toString());
    formData.append("immagine", fileSelezionato);
    return this.http.post<MessageResponse>(this.backendUrl + 'pubblica/' + localStorage.getItem('utenteId') || '', formData, {headers: this.getHeader()});
  }

  /**
   * Metodo per modificare un prodotto
   * @param marca marca modificata
   * @param modello modello modificato
   * @param prezzo prezzo modificato
   * @param misura misura modificata
   * @param id id del prodotto da modificare
   */
  modifica(marca: string, modello: string, prezzo: number, misura: number, id: number): Observable<MessageResponse> {
    const request: ModificaProdottoRequest = {marca: marca, modello: modello, prezzo: prezzo, misura: misura, id: id};
    return this.http.put<MessageResponse>(this.backendUrl + 'modifica/', request, {headers: this.getHeader()});
  }

  /**
   * Metodo per prendere tutti i prodotti non venduti
   */
  getAllNonVendute(): Observable<ProdottoResponse[]> {
    return this.http.get<ProdottoResponse[]>(this.backendUrl + 'getAllNonVendute/', {headers: this.getHeader()});
  }

  /**
   * Metodo per prendere la scarpa con l'id passato
   * @param scarpaId id della scarpa
   */
  dettagliScarpa(scarpaId: number): Observable<ProdottoResponse> {
    return this.http.get<ProdottoResponse>(this.backendUrl + 'get/' + scarpaId, {headers: this.getHeader()});
  }

  /**
   * Metodo per prendere tutti i prodotti
   */
  getAll(): Observable<ProdottoResponse[]> {
    return this.http.get<ProdottoResponse[]>(this.backendUrl + 'getAll/', {headers: this.getHeader()});
  }

  /**
   * Metodo per prendere tutti i prodotti pubblicati dall'utente con l'id passato
   * @param utenteId id dell'utente
   */
  getAllPubblicate(utenteId: number): Observable<ProdottoResponse[]> {
    return this.http.get<ProdottoResponse[]>(this.backendUrl + 'getAllPubblicate/' + utenteId, {headers: this.getHeader()});
  }

  /**
   * Metodo per eliminare un prodotto
   * @param id id del prodotto da eliminare
   */
  eliminaProdotto(id: number): Observable<MessageResponse> {
    return this.http.delete<MessageResponse>(this.backendUrl + 'elimina/' + id, {headers: this.getHeader()});
  }

  /**
   * Metodo per prendere tutti i prodotti pubblicati dall'utente loggato
   */
  getByCustomer(): Observable<ProdottoResponse[]> {
    return this.http.get<ProdottoResponse[]>(this.backendUrl + 'getByCustomer/' + localStorage.getItem('utenteId') || '', {headers: this.getHeader()});
  }

  /**
   * Metodo per prendere l'header delle richieste con il jwt
   */
  private getHeader(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': localStorage.getItem('jwt') ? `${localStorage.getItem('jwt')}` : '',
      id: localStorage.getItem('utenteId') ? `${localStorage.getItem('utenteId')}` : '',
      ruolo: localStorage.getItem('ruolo') ? `${localStorage.getItem('ruolo')}` : ''
    });
  }
}
