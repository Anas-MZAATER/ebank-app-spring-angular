import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CustomerService} from "../services/customer.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-customers',
  standalone: false,
  templateUrl: './customers.html',
  styleUrl: './customers.css'
})
export class Customers implements OnInit {
  customers! : Observable<any>;
  // errorMessage:String|undefined;
  errorMessage!:object;

  constructor(private customersService:CustomerService) {
  }

  ngOnInit(): void {
    this.customers= this.customersService.getCustomers();
  }

}
