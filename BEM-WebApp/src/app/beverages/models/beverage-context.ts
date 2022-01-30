import {IRI} from "../../models/iri";

export interface BeverageContext {
  id: IRI;
  beverage: IRI;
  isContextBeveragePreferred: boolean;
  event: string;
  location: string;
  season: string;
}
