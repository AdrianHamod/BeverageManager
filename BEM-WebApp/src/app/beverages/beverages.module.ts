import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BeverageListComponent} from './beverage-list/beverage-list.component';
import {BeverageDetailComponent} from './beverage-detail/beverage-detail.component';
import {RouterModule} from "@angular/router";
import {SharedModule} from "primeng/api";
import {DataViewModule} from "primeng/dataview";
import {DropdownModule} from "primeng/dropdown";
import {RatingModule} from "primeng/rating";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatListModule} from "@angular/material/list";
import {InputTextModule} from "primeng/inputtext";
import {ButtonModule} from "primeng/button";
import {TagModule} from "primeng/tag";
import {AutoCompleteModule} from "primeng/autocomplete";
import {MatInputModule} from "@angular/material/input";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {TagListComponent} from '../shared/tag-list/tag-list.component';
import {ImageModule} from 'primeng/image';
import {MatCardModule} from "@angular/material/card";
import {CardModule} from "primeng/card";
import {BreadcrumbModule} from "primeng/breadcrumb";
import {BeverageContextModalComponent} from './beverage-context-modal/beverage-context-modal.component';
import {DynamicDialogModule} from "primeng/dynamicdialog";
import {SelectButtonModule} from "primeng/selectbutton";

@NgModule({
    declarations: [
        BeverageListComponent,
        BeverageDetailComponent,
        TagListComponent,
        BeverageContextModalComponent
    ],
    exports: [
        TagListComponent
    ],
    imports: [
        CommonModule,
        RouterModule.forChild([
            {path: 'beverages/:name', component: BeverageListComponent},
            {path: 'pdp', component: BeverageDetailComponent}
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
        SelectButtonModule
    ]
})
export class BeveragesModule {
}
