package khouya.site.exam.repositories;

import khouya.site.exam.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, String> {
    List<Credit> getCreditByClient_Id(Long clientId);
} 