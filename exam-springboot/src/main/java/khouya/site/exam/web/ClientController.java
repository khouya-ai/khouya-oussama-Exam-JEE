package khouya.site.exam.web;

import khouya.site.exam.dtos.ClientDTO;
import khouya.site.exam.dtos.CreditDTO;
import khouya.site.exam.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
@CrossOrigin("*")
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ClientDTO save(@RequestBody ClientDTO clientDTO) {
        return clientService.saveClient(clientDTO);
    }

    @GetMapping("/{id}")
    public ClientDTO getClient(@PathVariable(name = "id") Long clientId) {
        return clientService.getClient(clientId);
    }

    @GetMapping
    public List<ClientDTO> listClients() {
        return clientService.listClients();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long clientId) {
        clientService.deleteClient(clientId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/credits")
    public List<CreditDTO> getClientCredits(@PathVariable(name = "id") Long clientId) {
        return clientService.getClientCredits(clientId);
    }

    @GetMapping("/{id}/total-credits")
    public Double calculateTotalCredits(@PathVariable(name = "id") Long clientId) {
        return clientService.calculateTotalCredits(clientId);
    }

    @GetMapping("/{id}/eligibilite")
    public ResponseEntity<Boolean> checkEligibility(@PathVariable(name = "id") Long clientId) {
        boolean isEligible = clientService.isEligibleForCredit(clientId);
        return ResponseEntity.ok(isEligible);
    }

    @GetMapping("/search")
    public List<ClientDTO> searchClients(@RequestParam(name = "keyword") String keyword) {
        return clientService.searchClients(keyword);
    }

    @PutMapping("/{id}")
    public ClientDTO updateClient(@PathVariable(name = "id") Long clientId, 
                                @RequestBody ClientDTO clientDTO) {
        clientDTO.setId(clientId);
        return clientService.saveClient(clientDTO);
    }
} 