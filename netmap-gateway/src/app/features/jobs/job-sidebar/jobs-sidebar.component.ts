import { Component, EventEmitter, Output, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputNumberModule } from 'primeng/inputnumber';
import { CheckboxModule } from 'primeng/checkbox';
import { MultiSelectModule } from 'primeng/multiselect';
import { debounceTime } from 'rxjs/operators';
import { ApiService } from '../../../shared/services/api.service';

@Component({
  selector: 'app-jobs-sidebar',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ButtonModule,
    InputNumberModule,
    CheckboxModule,
    MultiSelectModule
  ],
  templateUrl: './jobs-sidebar.component.html',
  styleUrls: ['./jobs-sidebar.component.scss']
})
export class JobsSidebarComponent implements OnInit {
  @Output() filterChange = new EventEmitter<any>();
  form: FormGroup;
  skillsOptions: string[] = [];

  constructor(private fb: FormBuilder, private api: ApiService) {
    this.form = this.fb.group({
      is_remote: [null],
      is_freelance: [null],
      skills: [[]],
      radius_km: [100]
    });
  }

  ngOnInit(): void {
    this.api.getAllSkills().subscribe((skills: any[]) => {
      // skills = [{ id, name }]
      this.skillsOptions = skills;
      this.emitFilter();
    });

    this.form.valueChanges.pipe(debounceTime(300)).subscribe(() => {
      this.emitFilter();
    });
  }



  private emitFilter(): void {
    const raw = this.form.value;

    const filter = {
      is_remote: raw.is_remote === true ? true : null,
      is_freelance: raw.is_freelance === true ? true : null,
      skills: Array.isArray(raw.skills) ? raw.skills : [],
      user_latitude: 39.9208,
      user_longitude: 32.8541,
      radius_km: raw.radius_km || 100
    };

    this.filterChange.emit(filter);
  }
}
