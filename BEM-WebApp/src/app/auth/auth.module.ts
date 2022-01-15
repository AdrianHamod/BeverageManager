import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LoginPageComponent} from "./pages/login-page/login-page.component";
import {InputTextModule} from 'primeng/inputtext';
import {ButtonModule} from "primeng/button";
import {FormsModule} from "@angular/forms";
import {PasswordModule} from "primeng/password";
import {RegisterPageComponent} from "./pages/register-page/register-page.component";
import {RouterModule} from "@angular/router";


@NgModule({
  declarations: [
    LoginPageComponent,
    RegisterPageComponent
  ],
  imports: [
    CommonModule,
    InputTextModule,
    ButtonModule,
    FormsModule,
    PasswordModule,
    RouterModule
  ],
  exports: [
  ]
})
export class AuthModule { }
