import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardPageComponent} from "./dash/pages/dashboard-page/dashboard-page.component";
import {LoginPageComponent} from "./auth/pages/login-page/login-page.component";
import {RegisterPageComponent} from "./auth/pages/register-page/register-page.component";

const routes: Routes = [
  {path: 'login', component: LoginPageComponent},
  {path: 'register', component: RegisterPageComponent},
  {path: '**', component: DashboardPageComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
