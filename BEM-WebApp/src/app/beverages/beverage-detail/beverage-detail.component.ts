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

    const drinkId = Number(this.route.snapshot.paramMap.get('id'));

    this.sub = this.beverageService.getDrinkById(Number(drinkId)).subscribe({
      next: value => {
        console.log(value);
        this.beverage = value.drinks[0];
      }
    })
  }

}
