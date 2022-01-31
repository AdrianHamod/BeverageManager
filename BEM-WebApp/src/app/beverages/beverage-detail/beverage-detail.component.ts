import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {BeverageService} from '../beverage.service';
import {Beverage} from '../models/beverage';
import {MenuItem} from "primeng/api";
import {AuthService} from "../../auth/auth.service";
import {DialogService} from "primeng/dynamicdialog";
import {BeverageContextModalComponent} from "../beverage-context-modal/beverage-context-modal.component";
import {BeverageModel} from "../models/beverage-model";

@Component({
  selector: 'app-beverage-detail',
  templateUrl: './beverage-detail.component.html',
  styleUrls: ['./beverage-detail.component.scss']
})
export class BeverageDetailComponent implements OnInit {

  beverageModel: BeverageModel;
  beverage: Beverage;
  sub!: Subscription;

  beveragePath: MenuItem[];
  home: MenuItem;

  constructor(
    public authService: AuthService,
    private beverageService: BeverageService,
    private route: ActivatedRoute,
    private dialogService: DialogService,
    private router: Router) {
  }

  ngOnInit(): void {

    this.route.queryParams.subscribe(params => {

      const drinkId = params["beverage"];

      if (drinkId === null)
        return;

      this.home = {icon: 'pi pi-home', routerLink: '/beverages'};

      const user = this.authService.getUserFromStorage();

      this.sub = this.beverageService.getDrinkByName(drinkId.toString(), user?.firstName ?? '').subscribe({
        next: value => {
          console.log(value);
          this.beverageModel = value;
          this.beverage = this.beverageModel.beverage;

          this.beveragePath = [
            {label: this.beverage.parent.localName, routerLink: `/beverages/${this.beverage.parent.localName}`},
            {label: this.beverage.name}
          ];
        }
      });
    });
  }

  openPreferencesPane() {
    const ref = this.dialogService.open(BeverageContextModalComponent, {
      data: {
        userBeverageContext: this.beverageModel.beverageContext,
        beverage: this.beverage,
        user: this.authService.getUserFromStorage()
      },
      header: this.beverage.name,
      width: '70%'
    });

    ref.onClose.subscribe((beverageContext: any[]) => {
      if (beverageContext[0])
        this.beverageModel.beverageContext = beverageContext[0];
    });
  }

}
