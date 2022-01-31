import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, Observable, of, tap} from "rxjs";
import {Beverages} from './models/beverages';
import {MessageService} from 'primeng/api';
import {environment} from "../../environments/environment";
import {BeverageModel} from "./models/beverage-model";
import {Profile} from "../models/profile";
import {UserPreferenceApiModel} from "./models/user-preference-api-model";
import {Beverage} from "./models/beverage";

@Injectable({
  providedIn: 'root'
})
export class BeverageService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Credentials': 'true',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET, POST, PATCH, DELETE, PUT, OPTIONS, PATCH',
      'Access-Control-Allow-Headers': 'Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With',
    })
  }

  constructor(
    private http: HttpClient,
    private messageService: MessageService) {
  }

  getDrinks(): Observable<Beverages> {

    return this.http.get<Beverages>(`${environment.apiUrl}/beverages`)
      .pipe(
        tap(_ => this.log('got beverages')),
        catchError(this.handleError<Beverages>('getBeverages'))
      );
  }

  getDrinkByName(drinkName: string, profileId?: string | null): Observable<BeverageModel> {
    return this.http.get<BeverageModel>(`${environment.apiUrl}/beverages/${drinkName}?profileId=${profileId}`)
      .pipe(
        tap(_ => this.log('got beverage')),
        catchError(this.handleError<BeverageModel>('getDrink'))
      );
  }


  registerUserPreference(profileId: string, userPreference: UserPreferenceApiModel) {
    return this.http.patch<Profile>(`${environment.apiUrl}/profiles/${profileId}`, userPreference, this.httpOptions)
      .pipe(
        tap(_ => this.log('added beverage preference')),
        catchError(this.handleError<Profile>('post preference'))
      );
  }

  updateUserPreference(profileId: string, beverageContextId: string, beverageContext: UserPreferenceApiModel) {
    return this.http.patch<Profile>(`${environment.apiUrl}/profiles/${profileId}/${beverageContextId}`, beverageContext, this.httpOptions)
      .pipe(
        tap(_ => this.log('updated beverage preference')),
        catchError(this.handleError<Profile>('patch preference update'))
      );
  }

  getBeveragesByTermInDescription(query: string) {
    return this.http.get<Beverage[]>(`${environment.apiUrl}/beverages/search/${query}`);
  }

  getBeveragesByParent(parentId: string) {
    return this.http.get<Beverage[]>(`${environment.apiUrl}/beverages/${parentId}/children`)
      .pipe(
        tap(_ => this.log('got beverages by parent')),
        catchError(this.handleError<Beverage[]>('error getting beverages by parent'))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.log(`${operation} failed: ${error.message}`);

      return of(result as T);
    }
  }

  private log(message: string) {
    this.messageService.add({severity: 'success', summary: 'Got data', detail: message});
  }
}
