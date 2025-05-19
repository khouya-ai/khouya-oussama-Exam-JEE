package khouya.site.exam.repositories;

import khouya.site.exam.entities.Credit;
import khouya.site.exam.enums.StatutCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {
    List<Credit> findByClientId(Long clientId);
    List<Credit> findByStatut(StatutCredit statut);
    
    @Query("SELECT c FROM Credit c WHERE TYPE(c) = :type")
    List<Credit> findByDiscriminatorValue(@Param("type") String type);
} 