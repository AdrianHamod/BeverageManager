import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MessageService} from "primeng/api";
import {catchError, Observable, of, tap} from "rxjs";
import {Profile} from "../models/profile";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService) {
  }


  getUserProfilePreferences(username: string): Observable<Profile> {
    return this.http.get<Profile>(`${environment.apiUrl}/profiles/${username}`)
      .pipe(
        tap(_ => console.log('got user profile')),
        catchError(this.handleError<Profile>('getProfile'))
      );

  }

  deleteUserProfilePreference(userId: string, beverageId: string): Observable<Profile> {
    return this.http.delete<Profile>(`${environment.apiUrl}/profiles/${userId}/${beverageId}`)
      .pipe(
        tap(_ => console.log('deleted preference')),
        catchError(this.handleError<Profile>('delete preference'))
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
