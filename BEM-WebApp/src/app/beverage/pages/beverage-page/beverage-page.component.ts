import { Component, OnInit } from '@angular/core';
import {Beverage} from "../../../common/components/Models/beverage";

@Component({
  selector: 'app-beverage-page',
  templateUrl: './beverage-page.component.html',
  styleUrls: ['./beverage-page.component.scss']
})
export class BeveragePageComponent implements OnInit {

  beverages: Array<Beverage>

  constructor() { }

  ngOnInit(): void {


  }

}
