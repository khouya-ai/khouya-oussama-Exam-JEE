package khouya.site.exam.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import khouya.site.exam.enums.TypeBien;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("IMMOBILIER")
public class CreditImmobilier extends Credit {
    @Enumerated(EnumType.STRING)
    private TypeBien typeBien;
}

