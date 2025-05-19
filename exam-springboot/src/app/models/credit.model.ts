import { StatutCredit } from './enums/statut-credit.enum';
import { Client } from './client.model';
import { Remboursement } from './remboursement.model';

export interface Credit {
    id?: number;
    dateDemande: Date;
    statut: StatutCredit;
    dateAcceptation?: Date;
    montant: number;
    dureeRemboursement: number;
    tauxInteret: number;
    client?: Client;
    remboursements?: Remboursement[];
    type: string; // 'PERSONNEL' | 'IMMOBILIER' | 'PROFESSIONNEL'
} 