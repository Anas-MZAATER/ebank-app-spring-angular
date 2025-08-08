import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CustomerService} from "../services/customer.service";
import {catchError, Observable, throwError} from "rxjs";
import {Customer} from "../model/customer.model";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-customers',
  standalone: false,
  templateUrl: './customers.html',
  styleUrl: './customers.css'
})
export class Customers implements OnInit {
  customers! : Observable<Array<Customer>>;
  // errorMessage:String|undefined;
  errorMessage!:object;
  //etude le cas d'utiliser undefined => Un test a chaque utilisation de l'attribut
  searchFormGroup : FormGroup | undefined;

  constructor(private customersService:CustomerService,
              private fb:FormBuilder) {
  }

  ngOnInit(): void {
      this.searchFormGroup=this.fb.group({
         keyword:this.fb.control("")
      });
      this.handleSearchCustomers();
  }

    handleSearchCustomers() {
      // "?" pour dire si il existe ou bien on doit fait un test
        let kw = this.searchFormGroup?.value.keyword;
        this.customers=this.customersService.searchCustomers(kw).pipe(
            catchError(err => {
                this.errorMessage=err.message;
                return throwError(err);
            })
        )
    }
}
