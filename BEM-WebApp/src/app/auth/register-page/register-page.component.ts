import { Component, OnInit } from '@angular/core';
import {AuthService} from "../auth.service";
import {Form, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {first} from "rxjs";
import {IUser} from "../../models/user/user";
import {MessageService} from "primeng/api";
import {Profile} from "../../models/profile";

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent implements OnInit {

  form: FormGroup;
  loading = false;
  submitted = false;

  genders: string[] = [
    "Male",
    "Female",
    "Other"
  ]

  user: IUser

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      emailAddress: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      age: ['', [Validators.required, Validators.max(123), Validators.min(8)]],
      country: ['', [Validators.required]],
      gender: ['', [Validators.required]]
    });
  }

  get firstName() { return this.form.get('firstName'); }
  get lastName() { return this.form.get('lastName'); }
  get emailAddress() { return this.form.get('emailAddress'); }
  get password() { return this.form.get('password'); }
  get age() {return this.form.get('age');}
  get country() {return this.form.get('country');}
  get gender() {return this.form.get('gender');}

  onSubmit(){
    this.submitted = true;

    this.messageService.clear();

    if (this.form.invalid)
      return;

    this.loading = true;
    this.authService.register(this.form.value)
      .pipe(first())
      .subscribe({
        next: () =>{

          this.authService.createProfile(
            this.form.value.firstName,
            this.form.value.age,
            this.form.value.country.alpha2Code,
            this.form.value.gender.toUpperCase()
          )
            .pipe(first())
            .subscribe({
              next: () =>{
                this.messageService.add({ severity: 'success', summary: 'register page', detail: 'Registration successful'});
                this.router.navigate(['../login'], {relativeTo: this.route});
              },
              error: err => {
                this.messageService.add({severity: 'error', summary: 'register page', detail: err});
                this.loading = false;
              }
            });
        },
        error: err => {
          this.messageService.add({severity: 'error', summary: 'register page', detail: err});
          this.loading = false;
        }
      })
  }

}
