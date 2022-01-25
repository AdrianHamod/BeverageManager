import {Component, Input, OnInit} from '@angular/core';
import {MenuItem} from "primeng/api";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'bem-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  @Input() title: string = '';

  items: MenuItem[];
  activeItem: MenuItem;

  constructor(
    public authService: AuthService
  ) { }

  ngOnInit(): void {
    this.items = [
      {label: 'Dashboard', icon: 'pi pi-fw pi-home', routerLink: '/', },
      { label: 'Profile', icon: 'pi pi-fw pi-user', routerLink: '/profile'},
      { label: 'Settings', icon: 'pi pi-fw pi-cog'},
      {label: 'Logout', icon:'pi pi-fw pi-sign-out', command: () => this.authService.logout(), style: {'margin-left': 'auto'}}
    ]

    this.activeItem = this.items[0];
  }
}
