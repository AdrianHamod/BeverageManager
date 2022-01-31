import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {MessageService} from "primeng/api";
import {catchError, Observable, of, tap} from "rxjs";
import {environment} from "../../environments/environment";
import {Beverage} from "../beverages/models/beverage";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

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

  getUserRecommendations(userId: string, count: number): Observable<Beverage[]>{
    return this.http.get<Beverage[]>(`${environment.apiUrl}/recommend/beverages?user=${userId}&count=${count}`)
      .pipe(
        tap(_ => this.log('got beverages')),
        catchError(this.handleError<Beverage[]>('getBeverages'))
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
