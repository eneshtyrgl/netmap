import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { MultiSelectModule } from 'primeng/multiselect';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { ButtonModule } from 'primeng/button';
import { ApiService } from '../../../shared/services/api.service';

@Component({
  selector: 'app-profile-edit',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    DropdownModule,
    MultiSelectModule,
    InputTextModule,
    InputNumberModule,
    ButtonModule,
  ],
  templateUrl: './profile-edit.component.html',
  styleUrls: ['./profile-edit.component.scss']
})
export class ProfileEditComponent implements OnInit {
  form!: FormGroup;
  role: string = localStorage.getItem('role') || '';
  skills: any[] = [];
  companies: any[] = [];
  isPatched: boolean = false;

  educationLevels = [
    'PRIMARY',
    'HIGH_SCHOOL',
    'ASSOCIATE',
    'BACHELOR',
    'MASTER',
    'PHD'
  ];

  constructor(private fb: FormBuilder, private api: ApiService, private router: Router) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      first_name: [''],
      last_name: [''],
      skills: [[]],
      experience_years: [null],
      education_level: [''],
      company_id: [null]
    });

    this.api.getAllSkills().subscribe((res) => {
      this.skills = res.map((s: any) => ({
        id: s.id,
        name: s.name
      }));
    });

    const userId = localStorage.getItem('user_id');
    if (!userId) return;

    this.api.getProfile(userId).subscribe((profile) => {
      const patched = {
        first_name: profile.first_name || '',
        last_name: profile.last_name || '',
        skills: profile.skills || [],
        experience_years: profile.experience_years ?? null,
        education_level: profile.education_level || '',
        company_id: profile.company_id ?? null
      };

      if (this.role === 'EMPLOYER') {
        this.api.getAllCompanies().subscribe((companies) => {
          this.companies = companies;
          this.form.patchValue(patched);
          this.isPatched = true;
        });
      } else {
        this.form.patchValue(patched);
        this.isPatched = true;
      }
    });
  }

  onSubmit() {
    const rawValue = this.form.value;

    if (Array.isArray(rawValue.skills) && rawValue.skills.length === 0) {
      rawValue.skills = null;
    }

    const filteredData = Object.fromEntries(
      Object.entries(rawValue).filter(([_, value]) =>
        value !== null && value !== '' && value !== undefined
      )
    );

    const userId = localStorage.getItem('user_id');
    if (!userId) return;

    this.api.updateProfile(userId, filteredData).subscribe({
      next: () => {
        alert('Profile updated!');
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error('Profile update failed:', err);
      }
    });
  }

  selectEducationLevel(level: string) {
    this.form.get('education_level')?.setValue(level);
  }

  onSkillToggle(skillId: string, event: Event) {
    const isChecked = (event.target as HTMLInputElement).checked;
    const skills = this.form.get('skills')?.value || [];

    const updatedSkills = isChecked
      ? [...skills, skillId]
      : skills.filter((id: string) => id !== skillId);

    this.form.get('skills')?.setValue(updatedSkills);
  }
}
