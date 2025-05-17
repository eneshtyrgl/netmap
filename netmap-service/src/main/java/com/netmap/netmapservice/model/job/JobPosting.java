package com.netmap.netmapservice.model.job;

import com.netmap.netmapservice.model.user.Employer;
import com.netmap.netmapservice.model.skill.Skill;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "job_posting")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPosting {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "post_date")
    private LocalDate postDate;

    @Column(name = "latitude", precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "is_remote")
    private Boolean isRemote;

    @Column(name = "is_freelance")
    private Boolean isFreelance;

    @ManyToMany
    @JoinTable(
            name = "job_posting_skill",
            joinColumns = @JoinColumn(name = "job_posting_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;
}
