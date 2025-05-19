import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  template: `
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">Gestion des Crédits</a>
        <div class="collapse navbar-collapse">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <a class="nav-link" routerLink="/customers" routerLinkActive="active">Clients</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  `,
  styles: [`
    .active {
      font-weight: bold;
      color: #0d6efd !important;
    }
  `]
})
export class NavbarComponent {
}
