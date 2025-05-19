import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JobPosting } from '../../../models/job-posting.model';
import { ApiService } from '../../../shared/services/api.service';
import { JobCardComponent } from '../job-card/job-card.component';
import { JobsSidebarComponent } from '../job-sidebar/jobs-sidebar.component';

@Component({
  selector: 'app-jobs-page',
  standalone: true,
  imports: [CommonModule, JobCardComponent, JobsSidebarComponent],
  templateUrl: './jobs-page.component.html',
  styleUrls: ['./jobs-page.component.scss']
})
export class JobsPageComponent {
  jobs: JobPosting[] = [];

  constructor(private api: ApiService) {}

  onFilterChange(filter: any): void {
    this.api.getFilteredJobs(filter).subscribe((data) => {
      this.jobs = data;
    });
  }
}
