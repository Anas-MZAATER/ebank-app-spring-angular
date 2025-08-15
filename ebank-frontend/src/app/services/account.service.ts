import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
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

  public debit(accountId:string,amount:number,description:string){
    let data={accountId:accountId,amount:amount,description:description}
    return this.http.post(environmentDevelopment.backendHost+"/accounts/debit",data);
  }

  public credit(accountId:string,amount:number,description:string){
    let data={accountId,amount,description}
    return this.http.post(environmentDevelopment.backendHost+"/accounts/credit",data);
  }

  public transfer(accountSource:string,accountDestination:string,amount:number,description:string){
    let data={accountSource,accountDestination,amount:amount,description:description}
    return this.http.post(environmentDevelopment.backendHost+"/accounts/transfer",data);
  }



}
