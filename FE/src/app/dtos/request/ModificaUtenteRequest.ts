export interface ModificaUtenteRequest {
  id: number;
  username: string;
  nome: string;
  cognome: string;
  vecchiaPassword: string;
  nuovaPassword: string;
}
