package khouya.site.exam.dtos;

import khouya.site.exam.enums.TypeBien;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
public class CreditImmobilierDTO extends CreditDTO {
    private TypeBien typeBien;
} 