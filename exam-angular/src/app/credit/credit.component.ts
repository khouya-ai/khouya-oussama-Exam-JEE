import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ClientService, CreditDTO } from '../services/client.service';

@Component({
  selector: 'app-credit',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mt-4">
      <div class="card">
        <div class="card-header d-flex justify-content-between align-items-center">
          <h3>Crédits du Client</h3>
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
                <th>Montant</th>
                <th>Date</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              <tr *ngFor="let credit of credits">
                <td>{{credit.id}}</td>
                <td>{{credit.amount}} DH</td>
                <td>{{credit.date | date:'dd/MM/yyyy'}}</td>
                <td>
                  <span class="badge" [ngClass]="{'bg-success': credit.status === 'PAID', 'bg-warning': credit.status === 'PENDING', 'bg-danger': credit.status === 'UNPAID'}">
                    {{credit.status}}
                  </span>
                </td>
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
  credits: CreditDTO[] = [];
  totalCredits: number = 0;
  isEligible: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private clientService: ClientService
  ) {}

  ngOnInit(): void {
    this.clientId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadCredits();
    this.loadTotalCredits();
    this.checkEligibility();
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