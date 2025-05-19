package khouya.site.exam.mappers;

import khouya.site.exam.dtos.RemboursementDTO;
import khouya.site.exam.entities.Remboursement;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class RemboursementMapper {
    
    public RemboursementDTO fromRemboursement(Remboursement remboursement) {
        RemboursementDTO remboursementDTO = new RemboursementDTO();
        BeanUtils.copyProperties(remboursement, remboursementDTO);
        if (remboursement.getCredit() != null) {
            remboursementDTO.setCreditId(remboursement.getCredit().getId());
        }
        return remboursementDTO;
    }
    
    public Remboursement fromRemboursementDTO(RemboursementDTO remboursementDTO) {
        Remboursement remboursement = new Remboursement();
        BeanUtils.copyProperties(remboursementDTO, remboursement);
        return remboursement;
    }
} 