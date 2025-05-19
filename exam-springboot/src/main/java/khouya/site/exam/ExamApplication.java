package khouya.site.exam;

import khouya.site.exam.entities.*;
import khouya.site.exam.enums.StatutCredit;
import khouya.site.exam.enums.TypeBien;
import khouya.site.exam.enums.TypeRemboursement;
import khouya.site.exam.repositories.ClientRepository;
import khouya.site.exam.repositories.CreditRepository;
import khouya.site.exam.repositories.RemboursementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class ExamApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamApplication.class, args);
	}

	@Bean
	CommandLineRunner start(ClientRepository customerRepository,
							CreditRepository bankAccountRepository,
							RemboursementRepository accountOperationRepository) {
		return args -> {
			Stream.of("Hassan", "Yassine", "Aicha").forEach(name -> {
				Client customer = new Client();
				customer.setNom(name);
				customer.setEmail(name + "@gmail.com");
				customerRepository.save(customer);
			});

			customerRepository.findAll().forEach(customer -> {
				CreditPersonnel creditPersonnel = new CreditPersonnel();
				creditPersonnel.setMotif("Credit Personnel");
				creditPersonnel.setDateAcceptation(new Date());
				creditPersonnel.setStatut(StatutCredit.ACCEPTE);
				creditPersonnel.setClient(customer);
				creditPersonnel.setMontant(9000.00);
				bankAccountRepository.save(creditPersonnel);

				CreditImmobilier creditImmobilier = new CreditImmobilier();
				creditImmobilier.setTypeBien(TypeBien.APPARTEMENT);
				creditImmobilier.setDateAcceptation(new Date());
				creditImmobilier.setStatut(StatutCredit.ACCEPTE);
				creditImmobilier.setClient(customer);
				creditImmobilier.setMontant(20000.00);
				bankAccountRepository.save(creditImmobilier);

				CreditProfessionnel creditProfessionnel = new CreditProfessionnel();
				creditProfessionnel.setMotif("creditProfessionnel");
				creditProfessionnel.setRaisonSociale("RaisonSociale");
				creditProfessionnel.setDateAcceptation(new Date());
				creditProfessionnel.setStatut(StatutCredit.ACCEPTE);
				creditProfessionnel.setClient(customer);
				creditProfessionnel.setMontant(80000.00);
				bankAccountRepository.save(creditProfessionnel);
			});

			bankAccountRepository.findAll().forEach(acc -> {
				for (int i = 0; i < 5; i++) {
					Remboursement accountOperation = new Remboursement();
					accountOperation.setDate(new Date());
					accountOperation.setMontant(Math.random() * 12000);
					accountOperation.setType(Math.random() > 0.5 ? TypeRemboursement.MENSUALITE : TypeRemboursement.REMBOURSEMENT_ANTICIPE);
					accountOperation.setCredit(acc);
					accountOperationRepository.save(accountOperation);
				}
			});

			Credit ba = bankAccountRepository.findAll().getFirst();
			System.out.println("***************************************");
			System.out.println("Bank Account ID: " + ba.getId());
			System.out.println("Bank Account Balance: " + ba.getMontant());
			System.out.println("Bank Account Created At: " + ba.getDateAcceptation());
			System.out.println("Bank Account Status: " + ba.getStatut());
			System.out.println("Bank Account Customer: " + ba.getClient().getNom());


		};
	}
}
