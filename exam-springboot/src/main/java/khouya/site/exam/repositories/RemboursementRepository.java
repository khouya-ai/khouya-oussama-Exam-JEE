package khouya.site.exam.repositories;

import khouya.site.exam.entities.Remboursement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RemboursementRepository extends JpaRepository<Remboursement, Long> {
    List<Remboursement> findByCreditId(Long credit_id);

    Page<Remboursement> findByCreditId(Long credit_id, Pageable pageable);
}