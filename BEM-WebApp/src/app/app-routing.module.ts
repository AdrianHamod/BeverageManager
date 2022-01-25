import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthGuard} from "./helpers/guard/auth.guard";
import {ProfilePageComponent} from "./profile/profile-page/profile-page.component";
import {BeverageListComponent} from "./beverages/beverage-list/beverage-list.component";

const authModule = () => import('./auth/auth.module').then(x => x.AuthModule);

const routes: Routes = [
  { path: '', component: BeverageListComponent, canActivate: [AuthGuard]},
  { path: 'auth', loadChildren: authModule},
  { path: 'profile', component: ProfilePageComponent},
  { path: '**', redirectTo: ''}
  // {path: 'login', component: LoginPageComponent},
  // {path: 'register', component: RegisterPageComponent},
  // {path: 'profile', component: ProfilePageComponent},
  // {path: 'settings', component: SettingsPageComponent},
  // {path: '**', component: DashboardPageComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
