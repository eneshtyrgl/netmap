import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {NgIf, NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-topbar',
  standalone: true,
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.scss'],
  imports: [
    RouterLink,
    NgIf,
    NgOptimizedImage
  ]
})
export class TopbarComponent {
  get username(): string {
    return localStorage.getItem('username') || 'User';
  }

  get role(): string | null {
    return localStorage.getItem('role');
  }


  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  constructor(private router: Router) {}

  goToProfile() {
    this.router.navigate(['/profile/edit']);
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']).then();
  }


}
