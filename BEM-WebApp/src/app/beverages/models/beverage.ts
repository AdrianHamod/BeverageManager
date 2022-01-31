import {IRI} from "../../models/iri";

export interface Beverage {
  beverageId: IRI;
  name: string;
  parent: IRI;
  description: string;
  imageUrl: string;
  allergens: string[];
}
