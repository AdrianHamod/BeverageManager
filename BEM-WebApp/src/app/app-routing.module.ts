import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardPageComponent} from "./dash/pages/dashboard-page/dashboard-page.component";
import {LoginPageComponent} from "./auth/pages/login-page/login-page.component";
import {RegisterPageComponent} from "./auth/pages/register-page/register-page.component";
import {ProfilePageComponent} from "./profile/pages/profile-page/profile-page.component";
import {SettingsPageComponent} from "./settings/pages/settings-page/settings-page.component";

const routes: Routes = [
  {path: 'login', component: LoginPageComponent},
  {path: 'register', component: RegisterPageComponent},
  {path: 'profile', component: ProfilePageComponent},
  {path: 'settings', component: SettingsPageComponent},
  {path: '**', component: DashboardPageComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
