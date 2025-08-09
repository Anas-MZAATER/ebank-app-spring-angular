import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Customer} from "../model/customer.model";
import {CustomerService} from "../services/customer.service";

@Component({
  selector: 'app-new-customer',
  standalone: false,
  templateUrl: './new-customer.html',
  styleUrl: './new-customer.css'
})
export class NewCustomer implements OnInit{
  newCustomerFormGroup!:FormGroup;

  constructor(private fb:FormBuilder,
              private customerService:CustomerService) {
  }

  ngOnInit(): void {
    this.newCustomerFormGroup=this.fb.group({
      name:this.fb.control(null),
      email:this.fb.control(null)
    });
  }


  handleSaveCustomer() {
    let customer:Customer=this.newCustomerFormGroup.value;
    this.customerService.saveCustomer(customer).subscribe({
      next:data=>{
        alert("customer saved successfully!")
      },
      error:err=>{
        console.log(err)
      }
    })
  }
}
