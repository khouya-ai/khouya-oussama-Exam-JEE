import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export enum StatutCredit {
  EN_COURS = 'EN_COURS',
  ACCEPTE = 'ACCEPTE',
  REJETE = 'REJETE'
}

export enum TypeRemboursement {
  MENSUALITE = 'MENSUALITE',
  REMBOURSEMENT_ANTICIPE = 'REMBOURSEMENT_ANTICIPE'
}

export enum TypeBien {
  APPARTEMENT = 'APPARTEMENT',
  MAISON = 'MAISON',
  TERRAIN = 'TERRAIN'
}

export interface ClientDTO {
  id?: number;
  nom?: string;
  email?: string;
  credits?: CreditDTO[];
}

export interface RemboursementDTO {
  id?: number;
  date?: Date;
  montant?: number;
  type?: TypeRemboursement;
  creditId?: number;
}

export interface CreditDTO {
  id?: number;
  dateDemande?: Date;
  statut?: StatutCredit;
  dateAcceptation?: Date;
  montant?: number;
  dureeRemboursement?: number;
  tauxInteret?: number;
  clientId?: number;
  remboursements?: RemboursementDTO[];
  type?: string;
}

export interface CreditPersonnelDTO extends CreditDTO {
  motif?: string;
}

export interface CreditImmobilierDTO extends CreditDTO {
  typeBien?: TypeBien;
}

export interface CreditProfessionnelDTO extends CreditDTO {
  motif?: string;
  raisonSociale?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private apiUrl = 'http://localhost:8080/api/clients';

  constructor(private http: HttpClient) { }

  // Save a new client or update existing one
  saveClient(client: ClientDTO): Observable<ClientDTO> {
    return this.http.post<ClientDTO>(this.apiUrl, client);
  }

  // Get a client by ID
  getClient(id: number): Observable<ClientDTO> {
    return this.http.get<ClientDTO>(`${this.apiUrl}/${id}`);
  }

  // Get all clients
  listClients(): Observable<ClientDTO[]> {
    return this.http.get<ClientDTO[]>(this.apiUrl);
  }

  // Delete a client
  deleteClient(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Get client credits
  getClientCredits(id: number): Observable<CreditDTO[]> {
    return this.http.get<CreditDTO[]>(`${this.apiUrl}/${id}/credits`);
  }

  // Calculate total credits
  calculateTotalCredits(id: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/${id}/total-credits`);
  }

  // Check client eligibility
  checkEligibility(id: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/${id}/eligibilite`);
  }

  // Search clients
  searchClients(keyword: string): Observable<ClientDTO[]> {
    return this.http.get<ClientDTO[]>(`${this.apiUrl}/search`, {
      params: { keyword }
    });
  }

  // Update client
  updateClient(id: number, client: ClientDTO): Observable<ClientDTO> {
    return this.http.put<ClientDTO>(`${this.apiUrl}/${id}`, client);
  }
} 