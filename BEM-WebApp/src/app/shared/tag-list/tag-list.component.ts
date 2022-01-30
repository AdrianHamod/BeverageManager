import {Component, Input, OnInit} from '@angular/core';
import {SeverityEnum} from 'src/app/beverages/enums/severity-enum';
import {Beverage} from 'src/app/beverages/models/beverage';
import {Tags} from 'src/app/beverages/models/tags';

@Component({
  selector: 'bem-tag-list',
  templateUrl: './tag-list.component.html',
  styleUrls: ['./tag-list.component.scss']
})
export class TagListComponent implements OnInit {

  @Input() beverage: Beverage;
  values: Tags[] = [];
  alergens: string[] = ['High Sugar', 'Gluen', 'Lacose', 'DEEZ NUTS'];

  ngOnInit(): void {

    console.log(this.beverage);

    this.beverage.allergens.forEach((allergen) => {
      this.values.push({name: allergen, severity: SeverityEnum.Danger});
    });

    // const alergenCount = Math.random() * this.alergens.length;
    //
    // for (let index = 0; index < alergenCount; index++) {
    //   const alergenIndex = Math.floor(Math.random() * alergenCount);
    //
    //   const chosenAlergen = this.alergens[alergenIndex];
    //
    //   if (this.values.findIndex(tag => tag.name === chosenAlergen) >= 0){
    //     continue;
    //   }
    //
    //   const severityIndex = Math.floor(Math.random() * Object.values(SeverityEnum).length);
    //
    //   const chosenSeverity = Object.values(SeverityEnum)[severityIndex] as SeverityEnum;
    //
    //   this.values.push({name: chosenAlergen, severity: chosenSeverity})
    // }
  }

}
