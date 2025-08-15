import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Navbar } from './navbar/navbar';
import { Customers } from './customers/customers';
import { Accounts } from './accounts/accounts';
import { HttpClientModule } from "@angular/common/http";
import {ReactiveFormsModule} from "@angular/forms";
import { NewCustomer } from './new-customer/new-customer';
import { CustomerAccounts } from './customer-accounts/customer-accounts';

@NgModule({
  declarations: [
    App,
    Navbar,
    Customers,
    Accounts,
    NewCustomer,
    CustomerAccounts
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners()
  ],
  bootstrap: [App]
})
export class AppModule { }
