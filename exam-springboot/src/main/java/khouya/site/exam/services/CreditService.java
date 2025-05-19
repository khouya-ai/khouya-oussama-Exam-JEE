package khouya.site.exam.services;

import khouya.site.exam.dtos.*;
import khouya.site.exam.enums.StatutCredit;

import java.util.List;

public interface CreditService {

    CreditDTO saveCredit(CreditDTO creditDTO);
    CreditDTO getCredit(Long creditId);
    List<CreditDTO> listCredits();
    void deleteCredit(Long creditId);
    

    CreditDTO updateCreditStatus(Long creditId, StatutCredit statut);
    List<CreditDTO> getCreditsByClient(Long clientId);
    List<CreditDTO> getCreditsByStatus(StatutCredit statut);
    

    Double calculateMensualite(Long creditId);
    Double calculateTotalInterets(Long creditId);
    

    List<CreditPersonnelDTO> getCreditsPersonnels();
    List<CreditImmobilierDTO> getCreditsImmobiliers();
    List<CreditProfessionnelDTO> getCreditsProfessionnels();
} 