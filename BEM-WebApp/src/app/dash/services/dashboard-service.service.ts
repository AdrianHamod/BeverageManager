import { Injectable } from '@angular/core';
import {Teams} from "../../common/components/Models/teams";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DashboardServiceService {

  httpOptions = {headers: new HttpHeaders({ 'Content-Type': 'application/json'})}

  constructor(private http: HttpClient) { }

  getTeamsTest(): Observable<Teams[]>{
    return this.http.get<Teams[]>('http://localhost:3000/api/teams');
  }
}
