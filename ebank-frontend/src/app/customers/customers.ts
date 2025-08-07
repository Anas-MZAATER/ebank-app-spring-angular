import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CustomerService} from "../services/customer.service";

@Component({
  selector: 'app-customers',
  standalone: false,
  templateUrl: './customers.html',
  styleUrl: './customers.css'
})
export class Customers implements OnInit {
  customers:any;
  // errorMessage:String|undefined;
  errorMessage!:object;

  constructor(private customersService:CustomerService) {
  }

  ngOnInit(): void {
    this.customersService.getCustomers().subscribe({
        next: (data)=>{
            this.customers=data;
        },
        error: (err)=>{
            // console.log(err);
            this.errorMessage=err.message;
        }
        }
    )
  }

}
