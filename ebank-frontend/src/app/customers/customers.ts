import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CustomerService} from "../services/customer.service";
import {catchError, map, Observable, throwError} from "rxjs";
import {Customer} from "../model/customer.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";

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
              private fb:FormBuilder,
              private router:Router) {
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

    handleDeleteCustomer(c: Customer) {
        /// message de confirmation
        let conf = confirm("are you sure?")
        if (!conf) return;
        /// suppression
        this.customersService.deleteCustomer(c.id).subscribe({
            next : (resp)=>{
                /// recharger la page a nouveau
                //this.handleSearchCustomers();
                /// supprimer la ligne
                this.customers=this.customers.pipe(
                    map(data => {
                        let index=data.indexOf(c);
                        data.slice(index,1);
                        return data;
                    })
                )
            },
            error : err => {
                console.log(err);
            }
        })
    }

    handleCustomerAccounts(customer: Customer) {
      ///transmis id et tout l'objet customer d'un composant a un autre a travers le systeme de routage
        this.router.navigateByUrl("/customer-accounts/"+customer.id,{state:customer});
    }
}
