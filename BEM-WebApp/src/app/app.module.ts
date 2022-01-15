import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { FormsModule } from '@angular/forms';
import {InputTextModule} from "primeng/inputtext";
import {AuthModule} from "./auth/auth.module";
import {DashModule} from "./dash/dash.module";
import {BeverageModule} from "./beverage/beverage.module";
import {ProfileModule} from "./profile/profile.module";
import {AppCommonModule} from "./common/common.module";

// @ts-ignore
@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    InputTextModule,
    AuthModule,
    DashModule,
    AppCommonModule,
    BeverageModule,
    ProfileModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
