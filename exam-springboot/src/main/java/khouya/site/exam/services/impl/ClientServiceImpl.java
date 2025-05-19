package khouya.site.exam.services.impl;

import khouya.site.exam.dtos.ClientDTO;
import khouya.site.exam.dtos.CreditDTO;
import khouya.site.exam.entities.Client;
import khouya.site.exam.enums.StatutCredit;
import khouya.site.exam.mappers.ClientMapper;
import khouya.site.exam.mappers.CreditMapper;
import khouya.site.exam.repositories.ClientRepository;
import khouya.site.exam.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final CreditMapper creditMapper;

    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        Client client = clientMapper.fromClientDTO(clientDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.fromClient(savedClient);
    }

    @Override
    public ClientDTO getClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        return clientMapper.fromClient(client);
    }

    @Override
    public List<ClientDTO> listClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::fromClient)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    @Override
    public List<CreditDTO> getClientCredits(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        return client.getCredits()
                .stream()
                .map(creditMapper::fromCredit)
                .collect(Collectors.toList());
    }

    @Override
    public Double calculateTotalCredits(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        return client.getCredits()
                .stream()
                .mapToDouble(credit -> credit.getMontant())
                .sum();
    }

    @Override
    public boolean isEligibleForCredit(Long clientId) {
        // Règles d'éligibilité simples :
        // 1. Le client ne doit pas avoir plus de 3 crédits en cours
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        double totalCredits = calculateTotalCredits(clientId);
        long nombreCreditsEnCours = client.getCredits()
                .stream()
                .filter(credit -> credit.getStatut() == StatutCredit.EN_COURS ||
                                credit.getStatut() == StatutCredit.ACCEPTE)
                .count();

        return  nombreCreditsEnCours < 3;
    }

    @Override
    public List<ClientDTO> searchClients(String keyword) {
        return clientRepository.findByNomContainsOrEmailContains(keyword, keyword)
                .stream()
                .map(clientMapper::fromClient)
                .collect(Collectors.toList());
    }
} 