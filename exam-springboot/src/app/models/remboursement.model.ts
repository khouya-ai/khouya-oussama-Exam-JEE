import { TypeRemboursement } from './enums/type-remboursement.enum';
import { Credit } from './credit.model';

export interface Remboursement {
    id?: number;
    date: Date;
    montant: number;
    type: TypeRemboursement;
    credit?: Credit;
} 