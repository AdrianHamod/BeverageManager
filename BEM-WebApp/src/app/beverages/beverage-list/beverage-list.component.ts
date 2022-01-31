import {Component, OnDestroy, OnInit} from '@angular/core';
import {map, Observable, startWith, Subscription} from "rxjs";
import {FormControl} from "@angular/forms";
import {Beverage} from "../models/beverage";
import {BeverageService} from "../beverage.service";
import {ActivatedRoute} from "@angular/router";
import {DashboardService} from "../../dashboard/dashboard.service";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-beverage-list',
  templateUrl: './beverage-list.component.html',
  styleUrls: ['./beverage-list.component.scss']
})
export class BeverageListComponent implements OnInit, OnDestroy {
  myControl = new FormControl();
  results: string[];
  filteredOptions: Observable<string[]>;
  sub!: Subscription;

  categoryBeverage: string;
  beverages: Beverage[];
  filteredBeverages: Beverage[];

  alergens: string[] = ["Gluten", "High Sugar"]

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
    private beverageService: BeverageService,
    private dashboardService: DashboardService,
    private authService: AuthService) {
  }

  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.categoryBeverage = params['name'];

      if (this.categoryBeverage !== undefined) {
        this.sub = this.beverageService.getBeveragesByParent(this.categoryBeverage).subscribe({
          next: value => {
            console.log(value);
            this.beverages = value;
            this.filteredBeverages = this.beverages;

            this.results = value.map(x => x.name);
            this.filteredOptions = this.myControl.valueChanges.pipe(
              startWith(''),
              map(query => this._filter(query))
            )
          }
        })
      } else {
        this.sub = this.beverageService.getDrinks().subscribe({
          next: value => {
            console.log(value);
            this.beverages = value.beverages;
            this.filteredBeverages = this.beverages;
          }
        });

        this.dashboardService.getUserRecommendations(this.authService.user?.firstName!, 20).subscribe({
          next: value => {
            console.log(value);

            if (value && value.length > 0) {
              this.results = value.filter(x => x.imageUrl != null).map(x => x.name);
              this.filteredOptions = this.myControl.valueChanges.pipe(
                startWith(''),
                map(query => this._filter(query))
              )
            }
            else {
              this.results = this.beverages.filter(x => x.imageUrl != null).map(x => x.name);
              this.filteredOptions = this.myControl.valueChanges.pipe(
                startWith(''),
                map(query => this._filter(query))
              )
            }
          }
        });

      }

    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    this.filteredBeverages = this.performFilter(filterValue);
    return this.results.filter(option => option.toLowerCase().includes(filterValue));
  }

  performFilter(filterBy: string): Beverage[] {
    filterBy = filterBy.toLocaleLowerCase();

    return this.beverages.filter((beverage: Beverage) => beverage.name.toLocaleLowerCase().includes(filterBy));
  }

}
