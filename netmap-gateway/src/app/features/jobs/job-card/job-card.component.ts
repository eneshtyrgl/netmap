import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JobPosting } from '../../../models/job-posting.model';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { Chip } from 'primeng/chip';
import { Router } from '@angular/router';
import { ApiService } from '../../../shared/services/api.service';

@Component({
  selector: 'app-job-card',
  standalone: true,
  imports: [CommonModule, CardModule, ButtonModule, Chip],
  templateUrl: './job-card.component.html',
  styleUrls: ['./job-card.component.scss']
})
export class JobCardComponent implements OnInit {
  @Input() job!: JobPosting;
  cityName: string = '';
  userId: string | null = null;
  userRole: string | null = null;

  constructor(
    private api: ApiService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getCityName(this.job.latitude, this.job.longitude);
    this.userId = localStorage.getItem('user_id');
    this.userRole = localStorage.getItem('role');
  }

  private getCityName(lat: number, lon: number): void {
// const url = `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lon}`;
// this.http.get<any>(url).subscribe({
//   next: (res) => {
//     this.cityName =
//       res.address?.city ||
//       res.address?.town ||
//       res.address?.village ||
//       res.address?.county ||
//       'Unknown';
//   },
//   error: () => {
//     this.cityName = 'Unknown';
//   }
// });
    this.cityName = 'Unknown';
  }

  applyToJob(): void {
    if (!this.userId) {
      alert('You must be logged in to apply!');
      this.router.navigate(['/login']);
      return;
    }

    const payload = {
      user_id: this.userId,
      job_posting_id: this.job.id
    };

    this.api.applyToJob(payload).subscribe({
      next: () => alert('Application submitted successfully!'),
      error: (err) => {
        if (err.status === 409) {
          alert('You have already applied to this job.');
        } else {
          alert('Application failed.');
          console.error(err);
        }
      }
    });
  }

  canDelete(): boolean {
    return this.userRole === 'EMPLOYER' || this.userRole === 'ADMIN';
  }

  deleteJob(): void {
    if (!this.userId) {
      alert('Unauthorized');
      return;
    }

    const confirmDelete = confirm('Are you sure you want to delete this job posting?');
    if (!confirmDelete) return;

    this.api.deleteJob(this.job.id, this.userId).subscribe({
      next: () => {
        alert('Job deleted successfully.');
        window.location.reload(); // İsteğe bağlı: sayfayı yenile
      },
      error: (err) => {
        alert('Failed to delete job.');
        console.error(err);
      }
    });
  }
}

