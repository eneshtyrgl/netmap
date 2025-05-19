import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs';
import {JobPosting} from '../../models/job-posting.model';


@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly BASE_URL = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  post<T>(path: string, data: any) {
    return this.http.post<T>(`${this.BASE_URL}${path}`, data);
  }

  get<T>(path: string) {
    return this.http.get<T>(`${this.BASE_URL}${path}`);
  }

  getAllJobs(): Observable<JobPosting[]> {
    return this.http.get<JobPosting[]>(`${this.BASE_URL}/jobs`);
  }

  getFilteredJobs(filter: any) {
    return this.http.post<JobPosting[]>(`${this.BASE_URL}/jobs/filter`, filter);
  }

  getAllSkills(): Observable<string[]> {
    return this.http.get<string[]>(`${this.BASE_URL}/skills`);
  }
}
