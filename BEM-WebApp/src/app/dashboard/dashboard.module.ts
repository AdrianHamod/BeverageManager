import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DashboardComponent} from "./dashboard.component";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {DataViewModule} from "primeng/dataview";
import {SharedModule} from "primeng/api";
import {DropdownModule} from "primeng/dropdown";
import {RatingModule} from "primeng/rating";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatListModule} from "@angular/material/list";
import {InputTextModule} from "primeng/inputtext";
import {ButtonModule} from "primeng/button";
import {TagModule} from "primeng/tag";
import {AutoCompleteModule} from "primeng/autocomplete";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {ImageModule} from "primeng/image";
import {MatCardModule} from "@angular/material/card";
import {CardModule} from "primeng/card";
import {BreadcrumbModule} from "primeng/breadcrumb";
import {DynamicDialogModule} from "primeng/dynamicdialog";
import {SelectButtonModule} from "primeng/selectbutton";
import {BeveragesModule} from "../beverages/beverages.module";
import {RouterModule} from "@angular/router";


@NgModule({
  declarations: [
    DashboardComponent
  ],
  imports: [
    RouterModule.forChild([
      {path: 'dashboard', component: DashboardComponent},
    ]),
    SharedModule,
    DataViewModule,
    DropdownModule,
    RatingModule,
    FormsModule,
    MatListModule,
    InputTextModule,
    ButtonModule,
    TagModule,
    AutoCompleteModule,
    MatInputModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatIconModule,
    ImageModule,
    MatCardModule,
    CardModule,
    BreadcrumbModule,
    DynamicDialogModule,
    SelectButtonModule,
    BeveragesModule,
    CommonModule
  ]
})
export class DashboardModule { }
