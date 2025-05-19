export interface JobPosting {
  id: string;
  title: string;
  description: string;
  salary: number;
  is_remote: boolean;
  is_freelance: boolean;
  latitude: number;
  longitude: number;
  post_date: string;
  skills: string[];
  verified: boolean;
  company_name: string;
}
