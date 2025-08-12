import {Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environmentDevelopment} from "../../environments/environment.development";
import {Observable} from "rxjs";
import {AccountDetails} from "../model/account.model";

@Injectable({
  providedIn: 'root'
})
export class AccountService{

  constructor(private http:HttpClient) {
  }

  public getAccount(accountId:string,page:number,size:number):Observable<AccountDetails>{
    return this.http.get<AccountDetails>(environmentDevelopment.backendHost+"/accounts/"+accountId+"/pageOperations?page="+page+"&size="+size);
  }


}
