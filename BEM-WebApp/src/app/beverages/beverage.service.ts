import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, Observable, of, tap} from "rxjs";
import { Beverages } from './models/beverages';
import { MessageService } from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class BeverageService {

  httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})}

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  getDrinks(): Observable<Beverages>{

    return this.http.get<Beverages>('https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Non_Alcoholic')
      .pipe(
        tap(_ => this.log('got beverages')),
        catchError(this.handleError<Beverages>('getTeams'))
      );
  }

  getDrinkById(drinkId: number): Observable<Beverages>{
    return this.http.get<Beverages>(`https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=${drinkId}`)
    .pipe(
      tap(_ => this.log('got beverage')),
      catchError(this.handleError<Beverages>('getDrink'))
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
