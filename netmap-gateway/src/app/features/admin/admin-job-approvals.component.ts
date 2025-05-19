import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../shared/services/api.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-job-approvals',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-job-approvals.component.html',
  styleUrls: ['./admin-job-approvals.component.scss']
})
export class AdminJobApprovalsComponent implements OnInit {
  jobs: any[] = [];
  loading = false;
  error: string | null = null;

  constructor(private api: ApiService) {}


  ngOnInit() {
    this.loadUnverifiedJobs();
  }

  loadUnverifiedJobs() {
    this.loading = true;
    this.api.get('/admin/jobs/unverified').subscribe({
      next: (data: any) => {
        this.jobs = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load jobs';
        this.loading = false;
      }
    });
  }

  verifyJob(jobId: string) {
    this.api.put(`/admin/jobs/${jobId}/verify`, {}).subscribe({
      next: () => {
        this.jobs = this.jobs.filter(j => j.id !== jobId);
      },
      error: () => alert('Failed to verify job')
    });
  }

}
