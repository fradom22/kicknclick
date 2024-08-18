export interface LoginResponse {
    utenteId: number;
    nome: string;
    cognome: string;
    username: string;
    ruolo: string;
    jwt: string;
}