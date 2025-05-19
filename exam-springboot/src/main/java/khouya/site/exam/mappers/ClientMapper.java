package khouya.site.exam.mappers;

import khouya.site.exam.dtos.ClientDTO;
import khouya.site.exam.entities.Client;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ClientMapper {
    private final CreditMapper creditMapper;

    public ClientMapper(CreditMapper creditMapper) {
        this.creditMapper = creditMapper;
    }

    public ClientDTO fromClient(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        BeanUtils.copyProperties(client, clientDTO);
        if (client.getCredits() != null) {
            clientDTO.setCredits(
                client.getCredits()
                    .stream()
                    .map(creditMapper::fromCredit)
                    .toList()
            );
        }
        return clientDTO;
    }

    public Client fromClientDTO(ClientDTO clientDTO) {
        Client client = new Client();
        BeanUtils.copyProperties(clientDTO, client);
        if (clientDTO.getCredits() != null) {
            client.setCredits(
                clientDTO.getCredits()
                    .stream()
                    .map(creditMapper::fromCreditDTO)
                    .toList()
            );
        }
        return client;
    }
} 