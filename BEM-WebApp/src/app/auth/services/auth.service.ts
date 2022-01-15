import { Injectable } from '@angular/core';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authKey: string;

  constructor(public router: Router) { }

  login(username: string, password: string) {
    // TODO: call API and check credentials

    this.router.navigate(["dashboard"]);
  }

  register(username: string, password: string) {
    // TODO: call API and log in user

    this.router.navigate(["dashboard"]);
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
