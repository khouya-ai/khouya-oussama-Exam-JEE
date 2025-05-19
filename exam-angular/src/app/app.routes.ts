import { Routes } from '@angular/router';
import { CustomersComponent } from './customers/customers.component';
import { CreditComponent } from './credit/credit.component';

export const routes: Routes = [
  { path: 'customers', component: CustomersComponent },
  { path: 'credit/:id', component: CreditComponent },
  { path: '', redirectTo: '/customers', pathMatch: 'full' }
];
