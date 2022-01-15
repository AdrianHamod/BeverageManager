import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HeaderComponent} from "./components/header/header.component";
import {HeaderNavigationComponent} from "./components/header-navigation/header-navigation.component";



@NgModule({
  declarations: [HeaderComponent,
  HeaderNavigationComponent],
  imports: [
    CommonModule
  ],
  exports: [
    HeaderComponent
  ]
})
export class AppCommonModule { }
