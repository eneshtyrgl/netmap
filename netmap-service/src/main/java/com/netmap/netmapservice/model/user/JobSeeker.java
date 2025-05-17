package com.netmap.netmapservice.model.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "job_seeker")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobSeeker {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser appUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_level")
    private EducationLevel educationLevel;

    @Column(name = "experience_years")
    private Integer experienceYears;

    public enum EducationLevel {
        PRIMARY,
        HIGH_SCHOOL,
        ASSOCIATE,
        BACHELOR,
        MASTER,
        PHD
    }
}
