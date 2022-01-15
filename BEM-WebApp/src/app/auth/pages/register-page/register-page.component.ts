import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent implements OnInit {

  registerEmailAddress: string;
  registerPassword: string;

  constructor(public authService: AuthService) {
    this.authService = authService;
  }

  ngOnInit(): void {
  }

}
