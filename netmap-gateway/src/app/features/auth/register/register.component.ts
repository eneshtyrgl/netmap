import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../../../shared/services/api.service';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-register',
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  form: FormGroup;

  constructor(private fb: FormBuilder, private api: ApiService, private router: Router) {
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
    console.log(this.form.value); // ← bunu ekle
    this.api.post('/auth/register', this.form.value).subscribe({
      next: () => this.router.navigate(['/login']),
      error: (err) => console.error('Registration error:', err)
    });
  }
}
