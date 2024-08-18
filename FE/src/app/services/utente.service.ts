import {Injectable} from '@angular/core';
import {globalBackendUrl} from '../utils/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ModificaUtenteRequest} from '../dtos/request/ModificaUtenteRequest';
import {MessageResponse} from '../dtos/response/MessageResponse';
import {UtenteResponse} from '../dtos/response/UtenteResponse';
import {UtentiGlobaliResponse} from '../dtos/response/UtentiGlobaliResponse';

/**
 * Classe service che si occupa di gestire le chiamate al backend per le operazioni relative all'utente.
 */
@Injectable({
  providedIn: 'root'
})
export class UtenteService {

  // Url del backend per l'UtenteController
  private backendUrl: string = globalBackendUrl + 'utente/';

  constructor(private http: HttpClient) {
  }

  /**
   * Metodo che prende i dati di un utente.
   * @param utenteId id dell'utente di cui si vogliono i dati.
   */
  get(utenteId: string): Observable<UtenteResponse> {
    return this.http.get<UtenteResponse>(this.backendUrl + 'get/' + utenteId, {headers: this.getHeader()});
  }

  /**
   * Metodo che modifica i dati di un utente.
   * @param request DTO con i dati dell'utente da modificare.
   */
  modifica(request: ModificaUtenteRequest): Observable<MessageResponse> {
    return this.http.put<MessageResponse>(this.backendUrl + 'modifica/', request, {headers: this.getHeader()});
  }

  /**
   * Metodo che elimina un utente.
   * @param id id dell'utente da eliminare.
   */
  elimina(id: string): Observable<MessageResponse> {
    return this.http.delete<MessageResponse>(this.backendUrl + 'elimina/' + id, {headers: this.getHeader()});
  }

  /**
   * Metodo che restituisce tutti gli utenti globali.
   */
  getAll(): Observable<UtentiGlobaliResponse[]> {
    return this.http.get<UtentiGlobaliResponse[]>(this.backendUrl + 'getAll', {headers: this.getHeader()});
  }

  /**
   * Metodo che restituisce l'header per le chiamate al backend, con il jwt e l'id dell'utente.
   */
  private getHeader(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': localStorage.getItem('jwt') ? `${localStorage.getItem('jwt')}` : '',
      id: localStorage.getItem('utenteId') ? `${localStorage.getItem('utenteId')}` : '',
      ruolo: localStorage.getItem('ruolo') ? `${localStorage.getItem('ruolo')}` : ''
    });
  }
}
