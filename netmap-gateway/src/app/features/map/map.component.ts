import { Component, AfterViewInit } from '@angular/core';
import * as L from 'leaflet';
import {ApiService} from '../../shared/services/api.service';
import {JobPosting} from '../../models/job-posting.model';

@Component({
  selector: 'app-map',
  standalone: true,
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements AfterViewInit {
  private map!: L.Map;

  constructor(private api: ApiService) {}

  ngAfterViewInit(): void {
    this.setCustomMarkerIcons();
    this.initMap();
    this.loadMarkers();
  }

  private loadMarkers(): void {
    this.api.getAllJobs().subscribe(jobs => {
      console.log(jobs);
      jobs.forEach(job => {
        L.marker([job.latitude, job.longitude])
          .addTo(this.map)
          .bindPopup(`<strong>${job.title}</strong><br>${job.company_name}`);
      });
    });
  }

  private initMap(): void {
    this.map = L.map('map').setView([39.9208, 32.8541], 6); // Ankara merkezli

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap contributors',
    }).addTo(this.map);

    L.marker([39.9208, 32.8541])
      .addTo(this.map)
      .bindPopup('Ankara');
  }

  private setCustomMarkerIcons(): void {
    const iconRetinaUrl = 'assets/leaflet/marker-icon-2x.png';
    const iconUrl = 'assets/leaflet/marker-icon.png';
    const shadowUrl = 'assets/leaflet/marker-shadow.png';

    L.Marker.prototype.options.icon = L.icon({
      iconRetinaUrl,
      iconUrl,
      shadowUrl,
      iconSize: [25, 41],
      iconAnchor: [12, 41],
      popupAnchor: [1, -34],
      shadowSize: [41, 41]
    });
  }
}
