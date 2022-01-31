import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../auth/auth.service";
import {IUser} from "../../models/user/user";
import {ProfileService} from "../profile.service";
import {Profile} from "../../models/profile";
import {BeverageContextModalComponent} from "../../beverages/beverage-context-modal/beverage-context-modal.component";
import {DialogService} from "primeng/dynamicdialog";
import {BeverageContext} from "../../beverages/models/beverage-context";

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.scss']
})
export class ProfilePageComponent implements OnInit {

  displayedColumns: string[] = ['name', 'preference', 'event', 'location', 'season', 'action'];

  userProfile: IUser;
  profileModel: Profile;

  constructor(
    public authService: AuthService,
    private profileService: ProfileService,
    private dialogService: DialogService
  ) {
  }

  ngOnInit(): void {
    this.authService.getById(this.authService.user!.id).subscribe({
      next: value => {
        this.userProfile = value;
      }
    });

    this.profileService.getUserProfilePreferences(this.authService.user!.firstName).subscribe({
      next: value => {
        this.profileModel = value;
      }
    });
  }

  deleteUserPreference(context: BeverageContext) {
    if (confirm("Are you sure you want to delete this preference?")) {
      this.profileService.deleteUserProfilePreference(this.authService.user!.firstName, context.id.localName).subscribe({
        next: value => {
          this.profileModel = value;
        }
      });
    }
  }

  openPreferencesPane(context: BeverageContext) {
    const ref = this.dialogService.open(BeverageContextModalComponent, {
      data: {
        userBeverageContext: context,
        user: this.authService.getUserFromStorage()
      },
      header: context.beverage.localName,
      width: '70%'
    });

    ref.onClose.subscribe((beverageContext: any[]) => {
      if (beverageContext[1])
        this.profileModel = beverageContext[1];
    });
  }

}
