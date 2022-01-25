import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser'
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {JwtInterceptor} from "./helpers/interceptor/jwt.interceptor";
import {ErrorInterceptor} from "./helpers/interceptor/error.interceptor";
import {mockBackendProvider} from "./helpers/interceptor/mock-backend.interceptor";
import {DataViewModule} from "primeng/dataview";
import {RatingModule} from "primeng/rating";
import { HeaderComponent } from './shared/header/header.component';
import {TabMenuModule} from "primeng/tabmenu";
import {InputTextModule} from "primeng/inputtext";
import {ButtonModule} from "primeng/button";
import {MenubarModule} from "primeng/menubar";
import {MatIconModule} from "@angular/material/icon";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {AuthModule} from "./auth/auth.module";
import {MdbCollapseModule} from "mdb-angular-ui-kit/collapse";
import {BeveragesModule} from "./beverages/beverages.module";
import { MessageService } from 'primeng/api';
import {ToastModule} from "primeng/toast";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    BeveragesModule,
    AuthModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    DataViewModule,
    RatingModule,
    FormsModule,
    TabMenuModule,
    InputTextModule,
    ButtonModule,
    MenubarModule,
    MatIconModule,
    BrowserAnimationsModule,
    MdbCollapseModule,
    ToastModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
    mockBackendProvider,
    MessageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
