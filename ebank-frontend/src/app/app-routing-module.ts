import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {Customers} from "./customers/customers";
import {Accounts} from "./accounts/accounts";

const routes: Routes = [
  { path : "customers", component : Customers},
  { path : "accounts", component : Accounts}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
