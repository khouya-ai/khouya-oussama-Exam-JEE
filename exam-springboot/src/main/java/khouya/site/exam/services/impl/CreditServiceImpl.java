package khouya.site.exam.services.impl;

import khouya.site.exam.dtos.*;
import khouya.site.exam.entities.*;
import khouya.site.exam.enums.StatutCredit;
import khouya.site.exam.mappers.CreditMapper;
import khouya.site.exam.repositories.CreditRepository;
import khouya.site.exam.services.CreditService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CreditServiceImpl implements CreditService {
    private final CreditRepository creditRepository;
    private final CreditMapper creditMapper;

    @Override
    public CreditDTO saveCredit(CreditDTO creditDTO) {
        Credit credit = creditMapper.fromCreditDTO(creditDTO);
        Credit savedCredit = creditRepository.save(credit);
        return creditMapper.fromCredit(savedCredit);
    }

    @Override
    public CreditDTO getCredit(Long creditId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new RuntimeException("Crédit non trouvé"));
        return creditMapper.fromCredit(credit);
    }

    @Override
    public List<CreditDTO> listCredits() {
        return creditRepository.findAll()
                .stream()
                .map(creditMapper::fromCredit)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCredit(Long creditId) {
        creditRepository.deleteById(creditId);
    }

    @Override
    public CreditDTO updateCreditStatus(Long creditId, StatutCredit statut) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new RuntimeException("Crédit non trouvé"));
        credit.setStatut(statut);
        Credit updatedCredit = creditRepository.save(credit);
        return creditMapper.fromCredit(updatedCredit);
    }

    @Override
    public List<CreditDTO> getCreditsByClient(Long clientId) {
        return creditRepository.findByClientId(clientId)
                .stream()
                .map(creditMapper::fromCredit)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditDTO> getCreditsByStatus(StatutCredit statut) {
        return creditRepository.findByStatut(statut)
                .stream()
                .map(creditMapper::fromCredit)
                .collect(Collectors.toList());
    }

    @Override
    public Double calculateMensualite(Long creditId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new RuntimeException("Crédit non trouvé"));
        
        // Formule de calcul de mensualité : M = C * (t/12) / (1 - (1 + t/12)^(-n))
        // où C est le capital emprunté, t le taux annuel et n le nombre de mois
        double montant = credit.getMontant();
        double tauxMensuel = credit.getTauxInteret() / 12 / 100; // Conversion en taux mensuel
        int duree = credit.getDureeRemboursement();
        
        return montant * tauxMensuel / (1 - Math.pow(1 + tauxMensuel, -duree));
    }

    @Override
    public Double calculateTotalInterets(Long creditId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new RuntimeException("Crédit non trouvé"));
        
        double mensualite = calculateMensualite(creditId);
        double totalPaiements = mensualite * credit.getDureeRemboursement();
        return totalPaiements - credit.getMontant();
    }

    @Override
    public List<CreditPersonnelDTO> getCreditsPersonnels() {
        return creditRepository.findByDiscriminatorValue("PERSONNEL")
                .stream()
                .map(credit -> (CreditPersonnel) credit)
                .map(credit -> {
                    CreditPersonnelDTO dto = new CreditPersonnelDTO();
                    BeanUtils.copyProperties(credit, dto);
                    dto.setType("PERSONNEL");
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditImmobilierDTO> getCreditsImmobiliers() {
        return creditRepository.findByDiscriminatorValue("IMMOBILIER")
                .stream()
                .map(credit -> (CreditImmobilier) credit)
                .map(credit -> {
                    CreditImmobilierDTO dto = new CreditImmobilierDTO();
                    BeanUtils.copyProperties(credit, dto);
                    dto.setType("IMMOBILIER");
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditProfessionnelDTO> getCreditsProfessionnels() {
        return creditRepository.findByDiscriminatorValue("PROFESSIONNEL")
                .stream()
                .map(credit -> (CreditProfessionnel) credit)
                .map(credit -> {
                    CreditProfessionnelDTO dto = new CreditProfessionnelDTO();
                    BeanUtils.copyProperties(credit, dto);
                    dto.setType("PROFESSIONNEL");
                    return dto;
                })
                .collect(Collectors.toList());
    }
} 