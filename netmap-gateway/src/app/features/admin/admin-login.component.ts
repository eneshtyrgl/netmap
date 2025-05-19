import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../../shared/services/api.service';
import { CommonModule } from '@angular/common';
import { Button } from 'primeng/button';
import { PasswordDirective } from 'primeng/password';
import { InputText } from 'primeng/inputtext';

@Component({
  selector: 'app-admin-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    InputText,
    PasswordDirective,
    Button,
  ],
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.scss']
})
export class AdminLoginComponent {
  form: FormGroup;

  constructor(private fb: FormBuilder, private api: ApiService, private router: Router) {
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.form.invalid) return;

    this.api.post('/admin/login', this.form.value).subscribe({
      next: (res: any) => {
        localStorage.setItem('token', res.token);
        localStorage.setItem('user_id', res.user_id);
        localStorage.setItem('username', res.username);
        localStorage.setItem('role', res.role);
        this.router.navigate(['/map']).then();
      },
      error: (err) => {
        console.error('Admin login error:', err);
      }
    });
  }
}
