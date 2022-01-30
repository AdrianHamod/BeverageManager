import {IRI} from "./iri";
import {Gender} from "./gender";
import {CountryCode} from "./country-code";
import {BeverageContext} from "../beverages/models/beverage-context";

export interface Profile {
  id: IRI;
  username: string;
  age: number;
  gender: Gender;
  countryCode: CountryCode;
  beveragePreferences: BeverageContext[];
}
