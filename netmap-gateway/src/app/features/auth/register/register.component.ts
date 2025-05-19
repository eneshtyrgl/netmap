import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import { ApiService } from '../../../shared/services/api.service';
import { CommonModule } from '@angular/common';
import { InputText } from 'primeng/inputtext';
import { PasswordDirective } from 'primeng/password';
import {Button} from 'primeng/button';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    InputText,
    PasswordDirective,
    RouterLink,
    Button,
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private api: ApiService,
    private router: Router
  ) {
    this.form = this.fb.group({
      first_name: ['', Validators.required],
      last_name: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', Validators.required],
      role: ['JOB_SEEKER', Validators.required],
      birth_date: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.form.invalid) return;

    this.api.post('/auth/register', this.form.value).subscribe({
      next: () => this.router.navigate(['/login']),
      error: (err) => console.error('Registration error:', err)
    });
  }
}
