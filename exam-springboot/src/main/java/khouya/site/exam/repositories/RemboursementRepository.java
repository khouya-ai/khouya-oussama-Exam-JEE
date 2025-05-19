package khouya.site.exam.repositories;

import khouya.site.exam.entities.Remboursement;
import khouya.site.exam.enums.TypeRemboursement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RemboursementRepository extends JpaRepository<Remboursement, Long> {
    List<Remboursement> findByCreditId(Long creditId);
    List<Remboursement> findByType(TypeRemboursement type);
    List<Remboursement> findByDateBetween(Date dateDebut, Date dateFin);

    Page<Remboursement> findByCreditId(Long credit_id, Pageable pageable);
}