import { Credit } from './credit.model';
import { TypeBien } from './enums/type-bien.enum';

export interface CreditImmobilier extends Credit {
    typeBien: TypeBien;
    type: 'IMMOBILIER';
} 