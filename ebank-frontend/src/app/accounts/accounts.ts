import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../services/account.service";
import {catchError, Observable, throwError} from "rxjs";
import {AccountDetails} from "../model/account.model";

@Component({
  selector: 'app-accounts',
  standalone: false,
  templateUrl: './accounts.html',
  styleUrl: './accounts.css'
})
export class Accounts implements OnInit {
  accountFormGroup! :FormGroup;
  currentPage:number=0;
  pageSize:number=5;
  accountObservable! : Observable<AccountDetails> // accountObservable === account$
  operationFormGroup! : FormGroup;
  errorMessage! : string;

  constructor(private fb:FormBuilder,
              private accountService:AccountService) {
  }

  ngOnInit(): void {
    this.accountFormGroup=this.fb.group({
      accountId : this.fb.control('')
      /// autre methode . formState:'': représente la valeur par défaut
      // accountId : ['',Validators.required()]
    })
    this.operationFormGroup=this.fb.group({
      operationType : this.fb.control(null),
      amount : this.fb.control(0),
      desc : this.fb.control(null),
      accountDestination : this.fb.control(null)
    })
  }


  handleSearchAccount() {
    let accountId = this.accountFormGroup.value.accountId;
    // this.accountService.getAccount(accountId,this.currentPage,this.pageSize).subscribe();
    /// OR
    this.accountObservable= this.accountService.getAccount(accountId,this.currentPage,this.pageSize).pipe(
        catchError(err => {
          this.errorMessage=err.error;
          return throwError(err);
        })
    )
  }

  goToPage(page: number) {
    this.currentPage=page;
    this.handleSearchAccount()
  }

  handleAccountOperation() {
    let accountId :string = this.accountFormGroup.value.accountId;
    let operationType= this.operationFormGroup.value.operationType;
    let amount :number = this.operationFormGroup.value.amount;
    let description :string = this.operationFormGroup.value.description;
    let accountDestination :string = this.operationFormGroup.value.accountDestination;
    if (operationType=='CREDIT'){
      this.accountService.credit(accountId,amount,description).subscribe({
        next :(dats) =>{
          alert("success credit");
          this.operationFormGroup.reset();
          this.handleSearchAccount();
        },
        error:(err)=>{
          console.log(err)
        }
      })
    }else if (operationType=='DEBIT'){
      this.accountService.debit(accountId,amount,description).subscribe({
        next :(dats) =>{
          alert("success debit");
          this.operationFormGroup.reset();
          this.handleSearchAccount();
        },
        error:(err)=>{
          console.log(err)
        }
      })
    }else if (operationType=='TRANSFER'){
      this.accountService.transfer(accountId,accountDestination,amount,description).subscribe({
        next :(dats) =>{
          alert("transfer debit");
          this.operationFormGroup.reset();
          this.handleSearchAccount();
        },
        error:(err)=>{
          console.log(err)
        }
      });
    }
  }
}
