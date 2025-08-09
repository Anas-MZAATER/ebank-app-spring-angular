import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Customer} from "../model/customer.model";
import {CustomerService} from "../services/customer.service";
import {catchError, throwError} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-customer',
  standalone: false,
  templateUrl: './new-customer.html',
  styleUrl: './new-customer.css'
})
export class NewCustomer implements OnInit{
  newCustomerFormGroup!:FormGroup;

  constructor(private fb:FormBuilder,
              private customersService:CustomerService,
              private router:Router) {
  }

  ngOnInit(): void {
    this.newCustomerFormGroup=this.fb.group({
      name:this.fb.control(null,[Validators.required,Validators.minLength(2)]),
      email:this.fb.control(null,[Validators.required,Validators.email])
    });
  }


  handleSaveCustomer() {
    let customer:Customer=this.newCustomerFormGroup.value;
    this.customersService.saveCustomer(customer).subscribe({
      next:data=>{
        alert("customer saved successfully!");
        // this.newCustomerFormGroup.reset();
        this.router.navigateByUrl("/customers")
      },
      error:err=>{
        console.log(err)
      }
    })
  }
}
