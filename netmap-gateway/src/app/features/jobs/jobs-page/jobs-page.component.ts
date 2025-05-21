import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JobPosting } from '../../../models/job-posting.model';
import { ApiService } from '../../../shared/services/api.service';
import { JobCardComponent } from '../job-card/job-card.component';
import { JobsSidebarComponent } from '../job-sidebar/jobs-sidebar.component';
import { FormsModule } from '@angular/forms';
import {InputText} from 'primeng/inputtext';

@Component({
  selector: 'app-jobs-page',
  standalone: true,
  imports: [CommonModule, FormsModule, JobCardComponent, JobsSidebarComponent, InputText],
  templateUrl: './jobs-page.component.html',
  styleUrls: ['./jobs-page.component.scss']
})
export class JobsPageComponent implements OnInit {
  jobs: JobPosting[] = [];
  filteredJobs: JobPosting[] = [];
  searchTerm: string = '';

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.loadAllJobs();
  }

  private loadAllJobs(): void {
    this.api.getAllJobs().subscribe((data) => {
      this.jobs = data;
      this.filteredJobs = data;
    });
  }

  onFilterChange(filter: any): void {
    const hasAnyFilter =
      filter.is_remote !== null ||
      filter.is_freelance !== null ||
      (Array.isArray(filter.skills) && filter.skills.length > 0) ||
      filter.top_applied === true;

    if (!hasAnyFilter) {
      this.loadAllJobs();
      return;
    }

    this.api.getFilteredJobs(filter).subscribe((data) => {
      this.jobs = data;
      this.filterJobsBySearch(); // filtre sonrası arama da uygula
    });
  }

  filterJobsBySearch(): void {
    const term = this.searchTerm.trim().toLowerCase();

    this.filteredJobs = this.jobs.filter(job =>
      job.title.toLowerCase().includes(term) ||
      job.company_name.toLowerCase().includes(term)
    );
  }
}
