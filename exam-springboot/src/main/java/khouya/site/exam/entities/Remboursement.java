package khouya.site.exam.entities;

import jakarta.persistence.*;
import khouya.site.exam.enums.TypeRemboursement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Remboursement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Date date;
    private Double montant;
    
    @Enumerated(EnumType.STRING)
    private TypeRemboursement type;
    
    @ManyToOne
    private Credit credit;
}

