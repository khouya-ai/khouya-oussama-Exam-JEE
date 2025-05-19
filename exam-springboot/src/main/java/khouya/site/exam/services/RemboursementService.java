package khouya.site.exam.services;

import khouya.site.exam.dtos.RemboursementDTO;
import khouya.site.exam.enums.TypeRemboursement;

import java.util.Date;
import java.util.List;

public interface RemboursementService {

    RemboursementDTO saveRemboursement(RemboursementDTO remboursementDTO);
    RemboursementDTO getRemboursement(Long remboursementId);
    List<RemboursementDTO> listRemboursements();
    void deleteRemboursement(Long remboursementId);
    

    List<RemboursementDTO> getRemboursementsByCredit(Long creditId);
    List<RemboursementDTO> getRemboursementsByType(TypeRemboursement type);
    List<RemboursementDTO> getRemboursementsByDateBetween(Date dateDebut, Date dateFin);
    Double getTotalRemboursementsByCredit(Long creditId);
    

    Double calculateTotalRemboursements();
    Double calculateTotalRemboursementsAnticipes();
} 