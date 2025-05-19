package khouya.site.exam.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ClientDTO {
    private Long id;
    private String nom;
    private String email;
    private List<CreditDTO> credits;
} 