import { Component, OnInit } from '@angular/core';
import {Beverage} from "../beverages/models/beverage";
import {ActivatedRoute} from "@angular/router";
import {BeverageService} from "../beverages/beverage.service";
import {FormControl} from "@angular/forms";
import {map, Observable, startWith, Subscription} from "rxjs";
import {DashboardService} from "./dashboard.service";
import {AuthService} from "../auth/auth.service";
import {Beverages} from "../beverages/models/beverages";
import {BeverageModel} from "../beverages/models/beverage-model";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  myControl = new FormControl();
  results: string[];
  filteredOptions: Observable<string[]>;
  sub!: Subscription;

  categoryBeverage: string;
  beverages: Beverage[];
  filteredBeverages: Beverage[];

  private _listFilter: string = '';

  get listFilter(): string {
    return this._listFilter;
  }

  set listFilter(value: string) {
    this._listFilter = value;
    console.log('In Setter: ', value);
    this.filteredBeverages = this.performFilter(value);
  }


  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private beverageService: BeverageService,
    private dashboardService: DashboardService
  ) { }

  ngOnInit(): void {
    this.sub = this.dashboardService.getUserRecommendations(this.authService.user?.firstName!, 20).subscribe({
      next: value => {
        console.log(value);

        this.beverages = value;
        this.filteredBeverages = this.beverages;

        this.results = this.beverages.filter(x => x.imageUrl != null).map(x => x.name);
        this.filteredOptions = this.myControl.valueChanges.pipe(
          startWith(''),
          map(query => this._filter(query))
        )
      }
    });
  }

  gOnDestroy() {
    this.sub.unsubscribe();
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    this.filteredBeverages = this.performFilter(filterValue);
    return this.results.filter(option => option.toLowerCase().includes(filterValue));
  }

  performFilter(filterBy: string): Beverage[] {
    filterBy = filterBy.toLocaleLowerCase();

    return this.beverages.filter((beverage: Beverage) => beverage.name ? beverage.name.toLocaleLowerCase().includes(filterBy) : false);
  }

}
