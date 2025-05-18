import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


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
}
