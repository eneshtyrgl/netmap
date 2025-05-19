import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../../../shared/services/api.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-job-post',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './job-post.component.html',
  styleUrls: ['./job-post.component.scss']
})
export class JobPostComponent implements OnInit {
  form: FormGroup;
  skillsOptions: string[] = [];
  selectedSkills: string[] = [];

  constructor(private fb: FormBuilder, private api: ApiService, private router: Router) {
    this.form = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      salary: [null, [Validators.required, Validators.min(0)]],
      latitude: [null],
      longitude: [null],
      is_remote: [false],
      is_freelance: [false],
    });
  }

  ngOnInit() {
    this.api.getAllSkills().subscribe({
      next: (skills) => {
        this.skillsOptions = skills.map(skill => skill.name);
      },
      error: (err) => console.error('Failed to load skills', err)
    });
  }


  onSkillChange(skill: string, event: Event) {
    const checked = (event.target as HTMLInputElement).checked;
    if (checked) {
      if (!this.selectedSkills.includes(skill)) this.selectedSkills.push(skill);
    } else {
      this.selectedSkills = this.selectedSkills.filter(s => s !== skill);
    }
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      alert('Please fill all required fields.');
      return;
    }
    if (this.selectedSkills.length === 0) {
      alert('Please select at least one skill.');
      return;
    }

    const formValue = {...this.form.value, skills: this.selectedSkills};

    this.api.post('/jobs', formValue).subscribe({
      next: () => this.router.navigate(['/jobs']),
      error: (err) => alert('Job posting failed: ' + (err.error?.message || err.message))
    });
  }
}
