import {Component, Input, OnInit} from '@angular/core';
import {MenuItem} from "primeng/api";
import {AuthService} from "../../auth/auth.service";
import {FormControl} from "@angular/forms";
import {Observable} from "rxjs";
import {BeverageService} from "../../beverages/beverage.service";
import {Beverage} from "../../beverages/models/beverage";
import {Router} from "@angular/router";

@Component({
  selector: 'bem-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  @Input() title: string = '';

  myControl = new FormControl();

  timeout: any = null;

  isLoading: boolean;
  items: MenuItem[];
  activeItem: MenuItem;

  options: string[];
  filteredOptions: Observable<string[]>;
  beverages: Beverage[];

  constructor(
    public authService: AuthService,
    private beverageService: BeverageService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.items = [
      {label: 'Dashboard', icon: 'pi pi-fw pi-home', routerLink: ['/', '/beverages/*'],},
      {label: 'Profile', icon: 'pi pi-fw pi-user', routerLink: '/profile'},
      {label: 'Settings', icon: 'pi pi-fw pi-cog'},
      {
        label: 'Logout',
        icon: 'pi pi-fw pi-sign-out',
        command: () => this.authService.logout(),
        style: {'margin-left': 'auto'}
      }
    ];

    this.myControl.valueChanges.subscribe({
      next: value => {
        if (value == '')
          this.options = [];
      }
    });

    this.activeItem = this.items[0];
  }

  beginTypeSearch(event: any) {
    this.isLoading = true;
    clearTimeout(this.timeout);
    var $this = this;
    this.timeout = setTimeout(function () {
      if (event.keyCode != 13) {
        $this.typeSearch(event.target.value);
      }
    }, 1000);
  }

  typeSearch(value: string) {
    console.log(value);

    this.beverageService.getBeveragesByTermInDescription(value).subscribe({
      next: value => {
        console.log(value);
        this.beverages = value;
        this.options = value.map(x => x.name);

      },
      error: err => {
        this.options = [];
      }
    });

    this.isLoading = false;
  }

  optionSelected(beverage: any) {
    this.router.navigate(['/pdp'], {
      queryParams: {
        beverage: beverage.beverageId.localName
      }
    }).then(r => {
      this.myControl.setValue('');
      this.options = [];
    });
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }
}
