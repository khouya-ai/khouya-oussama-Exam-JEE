import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ClientService, CreditDTO, ClientDTO, StatutCredit } from '../services/client.service';

@Component({
  selector: 'app-credit',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mt-4">
      <div class="card">
        <div class="card-header d-flex justify-content-between align-items-center">
          <h3>Crédits de {{client?.nom}}</h3>
          <div>
            <span class="badge bg-info me-2">Total des crédits: {{totalCredits}} DH</span>
            <span class="badge" [ngClass]="{'bg-success': isEligible, 'bg-danger': !isEligible}">
              {{isEligible ? 'Éligible' : 'Non Éligible'}}
            </span>
          </div>
        </div>
        <div class="card-body">
          <table class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Type</th>
                <th>Montant</th>
                <th>Date Demande</th>
                <th>Statut</th>
                <th>Durée (mois)</th>
                <th>Taux</th>
              </tr>
            </thead>
            <tbody>
              <tr *ngFor="let credit of credits">
                <td>{{credit.id}}</td>
                <td>{{credit.type}}</td>
                <td>{{credit.montant}} DH</td>
                <td>{{credit.dateDemande | date:'dd/MM/yyyy'}}</td>
                <td>
                  <span class="badge" [ngClass]="{
                    'bg-warning': credit.statut === 'EN_COURS',
                    'bg-success': credit.statut === 'ACCEPTE',
                    'bg-danger': credit.statut === 'REJETE'
                  }">
                    {{credit.statut}}
                  </span>
                </td>
                <td>{{credit.dureeRemboursement}}</td>
                <td>{{credit.tauxInteret}}%</td>
              </tr>
              <tr *ngIf="credits.length === 0">
                <td colspan="7" class="text-center">Aucun crédit trouvé pour ce client</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class CreditComponent implements OnInit {
  clientId!: number;
  client?: ClientDTO;
  credits: CreditDTO[] = [];
  totalCredits: number = 0;
  isEligible: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private clientService: ClientService
  ) {}

  ngOnInit(): void {
    this.clientId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadClientDetails();
    this.loadCredits();
    this.loadTotalCredits();
    this.checkEligibility();
  }

  loadClientDetails(): void {
    this.clientService.getClient(this.clientId).subscribe({
      next: (data) => {
        this.client = data;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des détails du client:', err);
      }
    });
  }

  loadCredits(): void {
    this.clientService.getClientCredits(this.clientId).subscribe({
      next: (data) => {
        this.credits = data;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des crédits:', err);
        alert('Erreur lors du chargement des crédits');
      }
    });
  }

  loadTotalCredits(): void {
    this.clientService.calculateTotalCredits(this.clientId).subscribe({
      next: (total) => {
        this.totalCredits = total;
      },
      error: (err) => {
        console.error('Erreur lors du calcul du total des crédits:', err);
      }
    });
  }

  checkEligibility(): void {
    this.clientService.checkEligibility(this.clientId).subscribe({
      next: (eligible) => {
        this.isEligible = eligible;
      },
      error: (err) => {
        console.error('Erreur lors de la vérification d\'éligibilité:', err);
      }
    });
  }
} 