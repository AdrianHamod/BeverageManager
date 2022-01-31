import {Component, OnInit} from '@angular/core';
import {BeverageService} from "../beverage.service";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BeverageContext} from "../models/beverage-context";
import {Beverage} from "../models/beverage";
import {first} from "rxjs";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-beverage-context-modal',
  templateUrl: './beverage-context-modal.component.html',
  styleUrls: ['./beverage-context-modal.component.scss']
})
export class BeverageContextModalComponent implements OnInit {

  loading: boolean;
  preferenceForm: FormGroup;

  submitted: boolean = false;

  likeOptions: any = [{label: 'Like', value: true}, {label: 'Dislike', value: false}];

  likeOption: boolean = false;

  preferenceModel: BeverageContext;
  beverage: Beverage;

  constructor(
    private beverageService: BeverageService,
    private ref: DynamicDialogRef,
    private config: DynamicDialogConfig,
    private formBuilder: FormBuilder,
    private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.beverage = this.config.data.beverage;
    this.preferenceModel = this.config.data.userBeverageContext;
    this.likeOption = this.preferenceModel?.isContextBeveragePreferred ?? false;

    this.preferenceForm = this.formBuilder.group({
      like: [this.preferenceModel?.isContextBeveragePreferred ?? false, Validators.required],
      event: [this.preferenceModel?.event ?? '', Validators.required],
      location: [this.preferenceModel?.location ?? '', Validators.required],
      season: [this.preferenceModel?.season ?? '', Validators.required]
    });
  }

  get like() {
    return this.preferenceForm.value.like;
  }

  get event() {
    return this.preferenceForm.value.event;
  }

  get location() {
    return this.preferenceForm.value.location;
  }

  get season() {
    return this.preferenceForm.value.season;
  }

  savePreference() {
    this.submitted = true;

    if (this.preferenceForm.invalid) {
      return;
    }

    this.loading = true;

    if (this.preferenceModel === undefined) {

      this.beverageService.registerUserPreference(this.config.data.user.firstName,
        {
          beverage: this.beverage.beverageId.localName,
          isContextBeveragePreferred: this.preferenceForm.value.like,
          event: this.preferenceForm.value.event,
          location: this.preferenceForm.value.location,
          season: this.preferenceForm.value.season
        })
        .pipe(first())
        .subscribe({
          next: profile => {
            this.messageService.add({
              severity: 'success',
              summary: 'Saved preferences',
              detail: 'User preferences saved'
            });
            this.ref.close([profile.beveragePreferences.filter(x => JSON.stringify(x.beverage) === JSON.stringify(this.beverage?.beverageId))[0], profile]);
          },
          error: err => {
            this.messageService.add({severity: 'error', summary: 'Save preferences', detail: err});
            this.loading = false;
          }
        });
    } else {
      this.beverageService.updateUserPreference(this.config.data.user.firstName, this.preferenceModel.id.localName, {
        beverage: this.preferenceModel.beverage.localName,
        isContextBeveragePreferred: this.preferenceForm.value.like,
        event: this.preferenceForm.value.event,
        location: this.preferenceForm.value.location,
        season: this.preferenceForm.value.season
      })
        .pipe(first())
        .subscribe({
          next: profile => {
            this.messageService.add({
              severity: 'success',
              summary: 'Updated preferences',
              detail: 'User preferences updated'
            });
            this.ref.close([profile.beveragePreferences.filter(x => JSON.stringify(x.beverage) === JSON.stringify(this.beverage?.beverageId))[0], profile]);
          },
          error: err => {
            this.messageService.add({severity: 'error', summary: 'Update preferences', detail: err});
            this.loading = false;
          }
        });
    }
  }
}
