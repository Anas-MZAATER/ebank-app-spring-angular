import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../services/account.service";
import {Observable} from "rxjs";
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

  constructor(private fb:FormBuilder,
              private accountService:AccountService) {
  }

  ngOnInit(): void {
    this.accountFormGroup=this.fb.group({
      accountId : this.fb.control('')
      /// autre methode . formState:'': représente la valeur par défaut
      // accountId : ['',Validators.required()]
    })
  }


  handleSearchAccount() {
    let accountId = this.accountFormGroup.value.accountId;
    // this.accountService.getAccount(accountId,this.currentPage,this.pageSize).subscribe();
    /// OR
    this.accountObservable= this.accountService.getAccount(accountId,this.currentPage,this.pageSize);
  }

  goToPage(page: number) {
    this.currentPage=page;
    this.handleSearchAccount()
  }
}
