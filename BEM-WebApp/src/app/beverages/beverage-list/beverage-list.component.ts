import {Component, OnDestroy, OnInit} from '@angular/core';
import {map, Observable, startWith, Subscription} from "rxjs";
import {FormControl} from "@angular/forms";
import {Beverage} from "../models/beverage";
import {BeverageService} from "../beverage.service";
import {Beverages} from "../models/beverages";

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

  beverages: Beverage[];
  filteredBeverages: Beverage[];

  alergens: string[] = ["Gluten", "High Sugar"]

  private _listFilter: string = '';

  get listFilter(): string{
    return this._listFilter;
  }

  set listFilter(value: string){
    this._listFilter = value;
    console.log('In Setter: ', value);
    this.filteredBeverages = this.performFilter(value);
  }

  constructor(
    private beverageService: BeverageService) { }

  ngOnInit(): void {

    this.sub = this.beverageService.getDrinks().subscribe({
      next: value => {
        console.log(value);
        this.beverages = value.beverages;
        this.filteredBeverages = this.beverages;

        this.results = value.beverages.map(x => x.name);
        this.filteredOptions = this.myControl.valueChanges.pipe(
          startWith(''),
          map(query => this._filter(query))
        )
      }
    });
  }

  ngOnDestroy(){
    this.sub.unsubscribe();
  }

  private _filter(value: string): string[]{
    const filterValue = value.toLowerCase();
    this.filteredBeverages = this.performFilter(filterValue);
    return this.results.filter(option => option.toLowerCase().includes(filterValue));
  }

  performFilter(filterBy: string): Beverage[]{
    filterBy = filterBy.toLocaleLowerCase();

    return this.beverages.filter((beverage: Beverage) => beverage.name.toLocaleLowerCase().includes(filterBy));
  }

}
