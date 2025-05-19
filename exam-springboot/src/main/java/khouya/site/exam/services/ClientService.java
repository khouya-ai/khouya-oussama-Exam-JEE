package khouya.site.exam.services;

import khouya.site.exam.dtos.ClientDTO;
import khouya.site.exam.dtos.CreditDTO;

import java.util.List;

public interface ClientService {

    ClientDTO saveClient(ClientDTO clientDTO);
    ClientDTO getClient(Long clientId);
    List<ClientDTO> listClients();
    void deleteClient(Long clientId);
    

    List<CreditDTO> getClientCredits(Long clientId);
    Double calculateTotalCredits(Long clientId);
    boolean isEligibleForCredit(Long clientId);
    List<ClientDTO> searchClients(String keyword);
} 