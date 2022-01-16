import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser'
import { HttpClientModule} from "@angular/common/http";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { FormsModule } from '@angular/forms';
import {InputTextModule} from "primeng/inputtext";
import {AuthModule} from "./auth/auth.module";
import {DashModule} from "./dash/dash.module";
import {BeverageModule} from "./beverage/beverage.module";
import {ProfileModule} from "./profile/profile.module";
import {AppCommonModule} from "./common/common.module";
import { BeveragePageComponent } from './beverage/pages/beverage-page/beverage-page.component';
import {DataViewModule} from "primeng/dataview";
import { ProfilePageComponent } from './profile/pages/profile-page/profile-page.component';
import { SettingsPageComponent } from './settings/pages/settings-page/settings-page.component';

// @ts-ignore
@NgModule({
  declarations: [
    AppComponent,
    BeveragePageComponent,
    ProfilePageComponent,
    SettingsPageComponent,
  ],
  imports: [
    BrowserModule,
      HttpClientModule,
    AppRoutingModule,
    FormsModule,
    InputTextModule,
    AuthModule,
    DashModule,
    AppCommonModule,
    BeverageModule,
    ProfileModule,
    DataViewModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
