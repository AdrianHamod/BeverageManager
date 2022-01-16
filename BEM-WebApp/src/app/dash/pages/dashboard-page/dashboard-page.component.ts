import { Component, OnInit } from '@angular/core';
import {DashboardServiceService} from "../../services/dashboard-service.service";
import {Teams} from "../../../common/components/Models/teams";

@Component({
  selector: 'app-dashboard-page',
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.scss']
})
export class DashboardPageComponent implements OnInit {

  teams: Teams[]

  constructor(private dashboardService: DashboardServiceService) { }

  ngOnInit(): void {
    this.dashboardService.getTeamsTest().subscribe((data: Teams[]) => this.teams = data);
  }

}
