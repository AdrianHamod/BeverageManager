import {BeverageContext} from "./beverage-context";

export interface Beverage {
    beverageId: number,
    name: string,
    parent: number,
    context: BeverageContext
}
