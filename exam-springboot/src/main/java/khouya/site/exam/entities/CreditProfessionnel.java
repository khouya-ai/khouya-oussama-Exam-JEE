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
@DiscriminatorValue("PROFESSIONNEL")
public class CreditProfessionnel extends Credit {
    private String motif;
    private String raisonSociale;
} 