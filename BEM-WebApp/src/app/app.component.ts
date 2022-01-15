import {Component, OnInit} from '@angular/core';
import {AuthService} from "./auth/services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'BEM-WebApp';

  constructor(public authService: AuthService) {
    this.authService = authService;
  }

  ngOnInit() {
    this.authService.saveToken("123");
  }
}
