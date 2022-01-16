import { Component, OnInit } from '@angular/core';
import {MenuItem} from "primeng/api";

@Component({
  selector: 'app-header-navigation',
  templateUrl: './header-navigation.component.html',
  styleUrls: ['./header-navigation.component.scss']
})
export class HeaderNavigationComponent implements OnInit {

  items: MenuItem[]
  activeItem: MenuItem;

  constructor() { }

  ngOnInit(): void {
    this.items = [
      {label: 'Dashboard', icon: 'pi pi-fw pi-home', routerLink: '/dashboard'},
      {label: 'Profile', icon:'pi pi-fw pi-user', routerLink: '/profile'},
      {label: 'Settings', icon:'pi pi-fw pi-cog', routerLink: '/settings'}
    ]

    this.activeItem = this.items[0];
  }

}
