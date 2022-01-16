import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DashboardPageComponent} from "./pages/dashboard-page/dashboard-page.component";
import {DataViewModule} from "primeng/dataview";
import {DropdownModule} from "primeng/dropdown";
import {RatingModule} from "primeng/rating";
import {FormsModule} from "@angular/forms";



@NgModule({
  declarations: [
    DashboardPageComponent
  ],
    imports: [
        CommonModule,
        DataViewModule,
        DropdownModule,
        RatingModule,
        FormsModule
    ],
  exports:[
    DashboardPageComponent
  ]
})
export class DashModule { }
