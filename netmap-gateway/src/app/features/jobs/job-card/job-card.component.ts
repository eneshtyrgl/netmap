import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JobPosting } from '../../../models/job-posting.model';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { HttpClient } from '@angular/common/http';
import {Chip} from 'primeng/chip';

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

  constructor(private http: HttpClient) {}

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
}
