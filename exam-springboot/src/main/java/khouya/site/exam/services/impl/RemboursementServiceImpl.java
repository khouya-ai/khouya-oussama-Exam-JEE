package khouya.site.exam.services.impl;

import khouya.site.exam.dtos.RemboursementDTO;
import khouya.site.exam.entities.Remboursement;
import khouya.site.exam.enums.TypeRemboursement;
import khouya.site.exam.mappers.RemboursementMapper;
import khouya.site.exam.repositories.RemboursementRepository;
import khouya.site.exam.services.RemboursementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class RemboursementServiceImpl implements RemboursementService {
    private final RemboursementRepository remboursementRepository;
    private final RemboursementMapper remboursementMapper;

    @Override
    public RemboursementDTO saveRemboursement(RemboursementDTO remboursementDTO) {
        Remboursement remboursement = remboursementMapper.fromRemboursementDTO(remboursementDTO);
        Remboursement savedRemboursement = remboursementRepository.save(remboursement);
        return remboursementMapper.fromRemboursement(savedRemboursement);
    }

    @Override
    public RemboursementDTO getRemboursement(Long remboursementId) {
        Remboursement remboursement = remboursementRepository.findById(remboursementId)
                .orElseThrow(() -> new RuntimeException("Remboursement non trouvé"));
        return remboursementMapper.fromRemboursement(remboursement);
    }

    @Override
    public List<RemboursementDTO> listRemboursements() {
        return remboursementRepository.findAll()
                .stream()
                .map(remboursementMapper::fromRemboursement)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRemboursement(Long remboursementId) {
        remboursementRepository.deleteById(remboursementId);
    }

    @Override
    public List<RemboursementDTO> getRemboursementsByCredit(Long creditId) {
        return remboursementRepository.findByCreditId(creditId)
                .stream()
                .map(remboursementMapper::fromRemboursement)
                .collect(Collectors.toList());
    }

    @Override
    public List<RemboursementDTO> getRemboursementsByType(TypeRemboursement type) {
        return remboursementRepository.findByType(type)
                .stream()
                .map(remboursementMapper::fromRemboursement)
                .collect(Collectors.toList());
    }

    @Override
    public List<RemboursementDTO> getRemboursementsByDateBetween(Date dateDebut, Date dateFin) {
        return remboursementRepository.findByDateBetween(dateDebut, dateFin)
                .stream()
                .map(remboursementMapper::fromRemboursement)
                .collect(Collectors.toList());
    }

    @Override
    public Double getTotalRemboursementsByCredit(Long creditId) {
        return remboursementRepository.findByCreditId(creditId)
                .stream()
                .mapToDouble(Remboursement::getMontant)
                .sum();
    }

    @Override
    public Double calculateTotalRemboursements() {
        return remboursementRepository.findAll()
                .stream()
                .mapToDouble(Remboursement::getMontant)
                .sum();
    }

    @Override
    public Double calculateTotalRemboursementsAnticipes() {
        return remboursementRepository.findByType(TypeRemboursement.REMBOURSEMENT_ANTICIPE)
                .stream()
                .mapToDouble(Remboursement::getMontant)
                .sum();
    }
} 