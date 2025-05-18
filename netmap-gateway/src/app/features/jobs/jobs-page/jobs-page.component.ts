import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JobPosting } from '../../../models/job-posting.model';
import { ApiService } from '../../../shared/services/api.service';
import {JobCardComponent} from '../job-card/job-card.component';

@Component({
  selector: 'app-jobs-page',
  standalone: true,
  imports: [CommonModule, JobCardComponent],
  templateUrl: './jobs-page.component.html',
  styleUrls: ['./jobs-page.component.scss']
})
export class JobsPageComponent implements OnInit {
  jobs: JobPosting[] = [];

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.api.getAllJobs().subscribe((data) => {
      this.jobs = data;
    });
  }
}
