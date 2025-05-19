import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ClientService, ClientDTO } from '../services/client.service';

@Component({
  selector: 'app-customers',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container mt-4">
      <div class="card">
        <div class="card-header">Liste des Clients</div>
        <div class="card-body">
          <!-- Formulaire d'ajout -->
          <form (ngSubmit)="handleAddClient()" class="mb-4">
            <div class="row">
              <div class="col-md-4">
                <input type="text" class="form-control" [(ngModel)]="newClient.nom" name="nom" placeholder="Nom">
              </div>
              <div class="col-md-4">
                <input type="email" class="form-control" [(ngModel)]="newClient.email" name="email" placeholder="Email">
              </div>
              <div class="col-md-4">
                <button type="submit" class="btn btn-primary">Ajouter</button>
              </div>
            </div>
          </form>

          <!-- Barre de recherche -->
          <div class="mb-3">
            <input 
              type="text" 
              class="form-control" 
              placeholder="Rechercher des clients..." 
              [(ngModel)]="searchKeyword"
              (ngModelChange)="handleSearch()">
          </div>

          <!-- Tableau des clients -->
          <table class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Email</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr *ngFor="let client of clients">
                <td>{{client.id}}</td>
                <td>{{client.nom}}</td>
                <td>{{client.email}}</td>
                <td>
                  <button class="btn btn-danger btn-sm me-2" (click)="handleDeleteClient(client.id!)">
                    Supprimer
                  </button>
                  <button class="btn btn-info btn-sm" (click)="handleViewCredits(client.id!)">
                    Crédits
                  </button>
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
export class CustomersComponent implements OnInit {
  clients: ClientDTO[] = [];
  newClient: ClientDTO = {};
  searchKeyword: string = '';

  constructor(
    private clientService: ClientService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients(): void {
    this.clientService.listClients().subscribe({
      next: (data) => {
        this.clients = data;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des clients:', err);
        alert('Erreur lors du chargement des clients');
      }
    });
  }

  handleAddClient(): void {
    if (!this.newClient.nom || !this.newClient.email) {
      alert('Veuillez remplir tous les champs');
      return;
    }

    this.clientService.saveClient(this.newClient).subscribe({
      next: () => {
        this.loadClients();
        this.newClient = {}; // Reset form
      },
      error: (err) => {
        console.error('Erreur lors de l\'ajout du client:', err);
        alert('Erreur lors de l\'ajout du client');
      }
    });
  }

  handleDeleteClient(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce client ?')) {
      this.clientService.deleteClient(id).subscribe({
        next: () => {
          this.loadClients();
        },
        error: (err) => {
          console.error('Erreur lors de la suppression du client:', err);
          alert('Erreur lors de la suppression du client');
        }
      });
    }
  }

  handleSearch(): void {
    if (this.searchKeyword.trim()) {
      this.clientService.searchClients(this.searchKeyword).subscribe({
        next: (data) => {
          this.clients = data;
        },
        error: (err) => {
          console.error('Erreur lors de la recherche:', err);
        }
      });
    } else {
      this.loadClients();
    }
  }

  handleViewCredits(clientId: number): void {
    this.router.navigate(['/credit', clientId]);
  }
}
