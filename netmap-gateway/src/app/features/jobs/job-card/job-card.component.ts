import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JobPosting } from '../../../models/job-posting.model';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { HttpClient } from '@angular/common/http';
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

  constructor(
    private http: HttpClient,
    private api: ApiService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getCityName(this.job.latitude, this.job.longitude);
  }

  private getCityName(lat: number, lon: number): void {
    const url = `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lon}`;
    this.http.get<any>(url).subscribe({
      next: (res) => {
        this.cityName =
          res.address?.city ||
          res.address?.town ||
          res.address?.village ||
          res.address?.county ||
          'Unknown';
      },
      error: () => {
        this.cityName = 'Unknown';
      }
    });
  }

  applyToJob() {
    const userId = localStorage.getItem('user_id');
    if (!userId) {
      alert('You must be logged in to apply!');
      this.router.navigate(['/login']);
      return;
    }

    const payload = {
      user_id: userId,
      job_posting_id: this.job.id
    };

    this.api.post('/applications', payload).subscribe({
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
}
