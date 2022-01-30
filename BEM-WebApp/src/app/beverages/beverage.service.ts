import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, Observable, of, tap} from "rxjs";
import { Beverages } from './models/beverages';
import { MessageService } from 'primeng/api';
import {environment} from "../../environments/environment";
import {Beverage} from "./models/beverage";
import {BeverageModel} from "./models/beverage-model";

@Injectable({
  providedIn: 'root'
})
export class BeverageService {

  httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*'})}

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  getDrinks(): Observable<Beverages>{

    return this.http.get<Beverages>(`${environment.apiUrl}/beverages`)
      .pipe(
        tap(_ => this.log('got beverages')),
        catchError(this.handleError<Beverages>('getBeverages'))
      );
  }

  getDrinkByName(drinkName: string): Observable<BeverageModel>{
    return this.http.get<BeverageModel>(`${environment.apiUrl}/beverages/${drinkName}`)
    .pipe(
      tap(_ => this.log('got beverage')),
      catchError(this.handleError<BeverageModel>('getDrink'))
    );
  }

  private handleError<T>(operation = 'operation', result? :T){
    return (error: any): Observable<T> =>{
      console.log(`${operation} failed: ${error.message}`);

      return of(result as T);
    }
  }

  private log(message: string){
    this.messageService.add({severity: 'success', summary: 'Got data', detail: message});
  }
}
