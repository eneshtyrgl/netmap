import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import { ApiService } from '../../../shared/services/api.service';
import {CommonModule} from '@angular/common';
import {Button} from 'primeng/button';
import {PasswordDirective} from 'primeng/password';
import {InputText} from 'primeng/inputtext';

@Component({
  selector: 'app-login',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    InputText,
    PasswordDirective,
    RouterLink,
    Button
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  form: FormGroup;

  constructor(private fb: FormBuilder, private api: ApiService, private router: Router) {
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    this.api.post('/auth/login', this.form.value).subscribe((res: any) => {
      localStorage.setItem('token', res.token);
      localStorage.setItem('user_id', res.user_id);
      localStorage.setItem('username', res.username);
      localStorage.setItem('role', res.role);
      this.router.navigate(['/map']).then();
    });
  }
}
