package khouya.site.exam.dtos;

import khouya.site.exam.enums.StatutCredit;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreditDTO {
    private Long id;
    private Date dateDemande;
    private StatutCredit statut;
    private Date dateAcceptation;
    private Double montant;
    private Integer dureeRemboursement;
    private Double tauxInteret;
    private Long clientId;
    private List<RemboursementDTO> remboursements;
    private String type;  // Pour identifier le type de crédit (PERSONNEL, IMMOBILIER, PROFESSIONNEL)
}