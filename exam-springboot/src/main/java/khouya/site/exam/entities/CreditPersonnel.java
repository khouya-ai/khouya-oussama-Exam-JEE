package khouya.site.exam.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("PERSONNEL")
public class CreditPersonnel extends Credit {
    private String motif; // ex: achat de voiture, études, travaux
} 