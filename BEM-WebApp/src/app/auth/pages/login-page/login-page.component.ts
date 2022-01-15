import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {

  loginEmailAddress: string;
  loginPassword: string;

  constructor(public authService: AuthService) {
    this.authService = authService;
  }

  ngOnInit(): void {
  }

}
