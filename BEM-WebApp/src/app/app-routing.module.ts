import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from "./helpers/guard/auth.guard";
import {ProfilePageComponent} from "./profile/profile-page/profile-page.component";
import {BeverageListComponent} from "./beverages/beverage-list/beverage-list.component";
import {DashboardComponent} from "./dashboard/dashboard.component";

const authModule = () => import('./auth/auth.module').then(x => x.AuthModule);

const routes: Routes = [
  {path: '', component: DashboardComponent, canActivate: [AuthGuard]},
  {path: 'beverages', component: BeverageListComponent},
  {path: 'auth', loadChildren: authModule},
  {path: 'profile', component: ProfilePageComponent, canActivate: [AuthGuard]},
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
