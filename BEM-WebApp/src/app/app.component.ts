import {Component, OnInit} from '@angular/core';
import {AuthService} from "./auth/services/auth.service";
import {PrimeNGConfig} from "primeng/api";
import {Router} from "@angular/router";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'BEM-WebApp';

  constructor(public authService: AuthService, private primengConfig: PrimeNGConfig, private route: Router) {
    this.authService = authService;
  }

  ngOnInit() {
    this.authService.saveToken("123");
    this.primengConfig.ripple = true;
  }

  setHeader() {
    let path = this.route.url.split('/')[1];
    this.title = decodeURIComponent(path);
  }
}
