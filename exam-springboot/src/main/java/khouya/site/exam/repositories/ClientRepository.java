package khouya.site.exam.repositories;

import khouya.site.exam.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("select c from Client  c where c.nom like :kw")
    List<Client> searchCustomer(@Param("kw") String keyword);

    List<Client> findClientsByNomContainingIgnoreCase(String keyword);
} 