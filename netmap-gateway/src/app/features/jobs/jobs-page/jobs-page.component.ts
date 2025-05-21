import { Component, OnInit } from '@angular/core';
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
export class JobsPageComponent implements OnInit {
  jobs: JobPosting[] = [];

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.loadAllJobs();
  }

  private loadAllJobs(): void {
    this.api.getAllJobs().subscribe((data) => {
      this.jobs = data;
    });
  }

  onFilterChange(filter: any): void {
    const hasAnyFilter =
      filter.is_remote !== null ||
      filter.is_freelance !== null ||
      (Array.isArray(filter.skills) && filter.skills.length > 0);

    if (!hasAnyFilter) {
      this.loadAllJobs();
      return;
    }

    this.api.getFilteredJobs(filter).subscribe((data) => {
      this.jobs = data;
    });
  }
}
