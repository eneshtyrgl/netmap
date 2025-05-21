import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JobPosting } from '../../models/job-posting.model';

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

  put<T>(path: string, data: any): Observable<T> {
    return this.http.put<T>(`${this.BASE_URL}${path}`, data);
  }

  getAllJobs(): Observable<JobPosting[]> {
    return this.http.get<JobPosting[]>(`${this.BASE_URL}/jobs`);
  }

  getFilteredJobs(filter: any): Observable<JobPosting[]> {
    return this.http.post<JobPosting[]>(`${this.BASE_URL}/jobs/filter`, filter);
  }

  getAllSkills(): Observable<any[]> {
    return this.http.get<any[]>(`${this.BASE_URL}/skills/all`);
  }

  getAllCompanies(): Observable<any[]> {
    return this.http.get<any[]>(`${this.BASE_URL}/companies`);
  }

  getProfile(userId: string): Observable<any> {
    return this.http.get<any>(`${this.BASE_URL}/users/${userId}`);
  }

  updateProfile(userId: string, data: any): Observable<any> {
    return this.http.put<any>(`${this.BASE_URL}/users/${userId}`, data);
  }

  applyToJob(data: { user_id: string, job_posting_id: string }) {
    return this.http.post(`${this.BASE_URL}/applications`, data);
  }
  deleteJob(jobId: string, userId: string) {
    return this.http.delete(`${this.BASE_URL}/jobs/${jobId}`, {
      headers: { 'X-User-Id': userId }
    });
  }

}
