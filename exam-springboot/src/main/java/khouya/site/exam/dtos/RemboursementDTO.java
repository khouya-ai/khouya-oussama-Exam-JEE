package khouya.site.exam.dtos;

import khouya.site.exam.enums.TypeRemboursement;
import lombok.Data;

import java.util.Date;

@Data
public class RemboursementDTO {
    private Long id;
    private Date date;
    private Double montant;
    private TypeRemboursement type;
    private Long creditId;
} 