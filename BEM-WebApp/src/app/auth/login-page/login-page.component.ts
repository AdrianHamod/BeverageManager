import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {first} from "rxjs";
import {MessageService} from "primeng/api";

@Component({
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  form: FormGroup;
  loading = false;
  submitted = false;

  loginEmailAddress: string;
  loginPassword: string;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    public authService: AuthService,
    private messageService: MessageService) {
    authService.skippedAuth = false;
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      emailAddress: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  get emailAddress() {
    return this.form.get('emailAddress');
  }

  get password() {
    return this.form.get('password');
  }

  onSubmit() {
    this.submitted = true;

    if (this.form.invalid) {
      return;
    }

    this.loading = true;

    this.authService.login(this.loginEmailAddress, this.loginPassword)
      .pipe(first())
      .subscribe({
        next: () => {
          this.router.navigateByUrl("/beverages");
        },
        error: err => {
          this.messageService.add({severity: 'error', summary: 'login page', detail: err});
          this.loading = false;
        }
      })
  }

  skipAuthentication(){
    this.authService.skippedAuth = true;
    this.router.navigateByUrl('/beverages');
  }

}
