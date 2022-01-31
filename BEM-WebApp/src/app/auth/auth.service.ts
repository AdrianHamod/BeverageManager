import {Injectable} from '@angular/core';
import {Router} from "@angular/router";
import {map} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {IUser} from "../models/user/user";
import {RegisterUserModel} from "../models/user/register-user-model";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  user: IUser | undefined;
  skippedAuth: boolean;

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Access-Control-Allow-Origin': '*'
    })
  }

  constructor(private router: Router, private http: HttpClient) {
    this.getUserFromStorage();
  }

  getUserFromStorage() {
    const localStorageJSON = localStorage.getItem('user');
    if (localStorageJSON) {
      const user = JSON.parse(localStorageJSON);
      this.user = user;
      return user;
    }
    return undefined;
  }

  login(emailAddress: string, password: string) {

    return this.http.post<IUser>(`${environment.apiUrl}/users/authenticate`, {emailAddress, password})
      .pipe(map(user => {
        localStorage.setItem('user', JSON.stringify(user));
        this.getUserFromStorage();
        return user;
      }));
  }

  logout() {
    localStorage.removeItem('user');
    this.user = undefined;
    this.router.navigate(['/auth/login']);
  }

  register(user: RegisterUserModel) {
    return this.http.post(`${environment.apiUrl}/users/register`, user);
  }

  getAll() {
    return this.http.get<IUser[]>(`${environment.apiUrl}/users`);
  }

  getById(id: number) {
    return this.http.get<IUser>(`${environment.apiUrl}/users/${id}`);
  }

  update(id: number, params?: any) {
    return this.http.put(`${environment.apiUrl}/users/${id}`, params)
      .pipe(map(x => {
        if (this.user) {
          if (id == this.user.id) {
            const user = {...this.user, ...params};
            localStorage.setItem('user', JSON.stringify(user));
            this.getUserFromStorage();
          }
        }

        return x;
      }));
  }

  delete(id: number) {
    return this.http.delete(`${environment.apiUrl}/users/${id}`)
      .pipe(map(x => {
        if (this.user) {
          if (id == this.user.id) {
            this.logout();
          }
        }
        return x;
      }));
  }

  createProfile(username: string, age: number, countryCode: string, gender: string) {
    return this.http.post(`${environment.apiUrl}/profiles`, {username, age, countryCode, gender});
  }

  saveToken(token: string) {
    localStorage.setItem("token", token);
  }

  removeToken() {
    localStorage.removeItem("token");
  }

  getToken() {
    return localStorage.getItem("token");
  }

  isLoggedIn() {
    return !!this.getToken();
  }

}
