import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HeaderComponent} from "./components/header/header.component";
import {HeaderNavigationComponent} from "./components/header-navigation/header-navigation.component";
import {MenubarModule} from "primeng/menubar";
import {InputTextModule} from "primeng/inputtext";
import {ButtonModule} from "primeng/button";
import {TabMenuModule} from "primeng/tabmenu";



@NgModule({
  declarations: [HeaderComponent,
  HeaderNavigationComponent],
    imports: [
        CommonModule,
        MenubarModule,
        InputTextModule,
        ButtonModule,
        TabMenuModule
    ],
    exports: [
        HeaderComponent,
        HeaderNavigationComponent
    ]
})
export class AppCommonModule { }
