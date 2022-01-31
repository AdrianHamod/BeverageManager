import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProfilePageComponent} from "./profile-page/profile-page.component";
import {MatCardModule} from "@angular/material/card";
import {MatTableModule} from "@angular/material/table";
import {TableModule} from "primeng/table";
import {ButtonModule} from "primeng/button";
import {MatButtonModule} from "@angular/material/button";
import {RouterModule} from "@angular/router";


@NgModule({
  declarations: [
    ProfilePageComponent
  ],
    imports: [
        CommonModule,
        MatCardModule,
        MatTableModule,
        TableModule,
        ButtonModule,
        MatButtonModule,
        RouterModule
    ]
})
export class ProfileModule {
}
