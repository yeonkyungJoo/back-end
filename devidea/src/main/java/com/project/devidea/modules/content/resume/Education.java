package com.project.devidea.modules.content.resume;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class Education {

    @Id @GeneratedValue
    @Column(name = "education_id")
    private Long id;
    private String schoolName;
    private String major;
    private LocalDateTime entranceDate;
    private LocalDateTime graduationDate;
    private double score;
    private String degree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
