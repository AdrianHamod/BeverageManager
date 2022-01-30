import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { BeverageService } from '../beverage.service';
import { Beverage } from '../models/beverage';

@Component({
  selector: 'app-beverage-detail',
  templateUrl: './beverage-detail.component.html',
  styleUrls: ['./beverage-detail.component.scss']
})
export class BeverageDetailComponent implements OnInit {

  beverage: Beverage;
  sub!: Subscription;

  constructor(
    private beverageService: BeverageService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {

    const drinkId = this.route.snapshot.paramMap.get('name');

    if (drinkId === null)
      return;

    this.sub = this.beverageService.getDrinkByName(drinkId.toString()).subscribe({
      next: value => {
        console.log(value);
        this.beverage = value.beverage;
      }
    })
  }

}
