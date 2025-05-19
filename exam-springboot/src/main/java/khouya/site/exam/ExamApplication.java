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
	CommandLineRunner start(ClientRepository clientRepository,
							CreditRepository creditRepository,
							RemboursementRepository remboursementRepository) {
		return args -> {
			// Création des clients
			Stream.of(
				new String[]{"Hassan Alami", "hassan.alami@gmail.com"},
				new String[]{"Yassine Mansouri", "yassine.mansouri@gmail.com"},
				new String[]{"Aicha Benani", "aicha.benani@gmail.com"}
			).forEach(data -> {
				Client client = new Client();
				client.setNom(data[0]);
				client.setEmail(data[1]);
				clientRepository.save(client);
			});

			clientRepository.findAll().forEach(client -> {
				// Crédit Personnel (voiture)
				CreditPersonnel creditVoiture = new CreditPersonnel();
				creditVoiture.setMotif("Achat voiture neuve");
				creditVoiture.setDateDemande(new Date());
				creditVoiture.setDateAcceptation(new Date());
				creditVoiture.setStatut(StatutCredit.ACCEPTE);
				creditVoiture.setClient(client);
				creditVoiture.setMontant(200000.00);
				creditVoiture.setDureeRemboursement(60);
				creditVoiture.setTauxInteret(4.5);
				creditRepository.save(creditVoiture);

				// Crédit Immobilier (appartement)
				CreditImmobilier creditAppartement = new CreditImmobilier();
				creditAppartement.setTypeBien(TypeBien.APPARTEMENT);
				creditAppartement.setDateDemande(new Date());
				creditAppartement.setDateAcceptation(new Date());
				creditAppartement.setStatut(StatutCredit.ACCEPTE);
				creditAppartement.setClient(client);
				creditAppartement.setMontant(900000.00);
				creditAppartement.setDureeRemboursement(240);
				creditAppartement.setTauxInteret(3.75);
				creditRepository.save(creditAppartement);

				// Crédit Professionnel (projet)
				CreditProfessionnel creditProjet = new CreditProfessionnel();
				creditProjet.setMotif("Achat équipement industriel");
				creditProjet.setRaisonSociale(client.getNom() + " & Co SARL");
				creditProjet.setDateDemande(new Date());
				creditProjet.setDateAcceptation(new Date());
				creditProjet.setStatut(StatutCredit.ACCEPTE);
				creditProjet.setClient(client);
				creditProjet.setMontant(500000.00);
				creditProjet.setDureeRemboursement(84);
				creditProjet.setTauxInteret(5.25);
				creditRepository.save(creditProjet);

				// Crédit en cours d'étude
				CreditPersonnel creditEnCours = new CreditPersonnel();
				creditEnCours.setMotif("Financement études supérieures");
				creditEnCours.setDateDemande(new Date());
				creditEnCours.setStatut(StatutCredit.EN_COURS);
				creditEnCours.setClient(client);
				creditEnCours.setMontant(150000.00);
				creditEnCours.setDureeRemboursement(36);
				creditEnCours.setTauxInteret(4.0);
				creditRepository.save(creditEnCours);
			});

			// Création des remboursements pour chaque crédit accepté
			creditRepository.findByStatut(StatutCredit.ACCEPTE).forEach(credit -> {
				double tauxMensuel = credit.getTauxInteret() / 12 / 100;
				double mensualite = credit.getMontant() * tauxMensuel / 
					(1 - Math.pow(1 + tauxMensuel, -credit.getDureeRemboursement()));

				// Création de 5 remboursements
				for (int i = 0; i < 5; i++) {
					Remboursement remboursement = new Remboursement();
					remboursement.setDate(new Date());
					remboursement.setMontant(mensualite);
					remboursement.setType(i < 4 ? TypeRemboursement.MENSUALITE : TypeRemboursement.REMBOURSEMENT_ANTICIPE);
					remboursement.setCredit(credit);
					remboursementRepository.save(remboursement);
				}
			});

			// Affichage des informations pour vérification
			System.out.println("***************************************");
			System.out.println("Données initiales créées avec succès :");
			System.out.println("- Nombre de clients : " + clientRepository.count());
			System.out.println("- Nombre de crédits : " + creditRepository.count());
			System.out.println("- Nombre de remboursements : " + remboursementRepository.count());
			System.out.println("***************************************");
		};
	}
}
