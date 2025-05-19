package khouya.site.exam.mappers;

import khouya.site.exam.dtos.*;
import khouya.site.exam.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CreditMapper {
    
    public CreditDTO fromCredit(Credit credit) {
        CreditDTO creditDTO;
        
        if (credit instanceof CreditPersonnel) {
            CreditPersonnelDTO dto = new CreditPersonnelDTO();
            BeanUtils.copyProperties(credit, dto);
            dto.setMotif(((CreditPersonnel) credit).getMotif());
            dto.setType("PERSONNEL");
            creditDTO = dto;
        }
        else if (credit instanceof CreditImmobilier) {
            CreditImmobilierDTO dto = new CreditImmobilierDTO();
            BeanUtils.copyProperties(credit, dto);
            dto.setTypeBien(((CreditImmobilier) credit).getTypeBien());
            dto.setType("IMMOBILIER");
            creditDTO = dto;
        }
        else if (credit instanceof CreditProfessionnel) {
            CreditProfessionnelDTO dto = new CreditProfessionnelDTO();
            BeanUtils.copyProperties(credit, dto);
            dto.setMotif(((CreditProfessionnel) credit).getMotif());
            dto.setRaisonSociale(((CreditProfessionnel) credit).getRaisonSociale());
            dto.setType("PROFESSIONNEL");
            creditDTO = dto;
        }
        else {
            throw new IllegalArgumentException("Type de crédit inconnu");
        }
        
        if (credit.getClient() != null) {
            creditDTO.setClientId(credit.getClient().getId());
        }
        
        return creditDTO;
    }
    
    public Credit fromCreditDTO(CreditDTO creditDTO) {
        Credit credit;
        
        switch (creditDTO.getType()) {
            case "PERSONNEL" -> {
                CreditPersonnel creditPersonnel = new CreditPersonnel();
                BeanUtils.copyProperties(creditDTO, creditPersonnel);
                if (creditDTO instanceof CreditPersonnelDTO) {
                    creditPersonnel.setMotif(((CreditPersonnelDTO) creditDTO).getMotif());
                }
                credit = creditPersonnel;
            }
            case "IMMOBILIER" -> {
                CreditImmobilier creditImmobilier = new CreditImmobilier();
                BeanUtils.copyProperties(creditDTO, creditImmobilier);
                if (creditDTO instanceof CreditImmobilierDTO) {
                    creditImmobilier.setTypeBien(((CreditImmobilierDTO) creditDTO).getTypeBien());
                }
                credit = creditImmobilier;
            }
            case "PROFESSIONNEL" -> {
                CreditProfessionnel creditProfessionnel = new CreditProfessionnel();
                BeanUtils.copyProperties(creditDTO, creditProfessionnel);
                if (creditDTO instanceof CreditProfessionnelDTO) {
                    creditProfessionnel.setMotif(((CreditProfessionnelDTO) creditDTO).getMotif());
                    creditProfessionnel.setRaisonSociale(((CreditProfessionnelDTO) creditDTO).getRaisonSociale());
                }
                credit = creditProfessionnel;
            }
            default -> throw new IllegalArgumentException("Type de crédit inconnu: " + creditDTO.getType());
        }
        
        return credit;
    }
} 