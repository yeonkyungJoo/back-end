package com.project.devidea.modules.content.resume;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Education {

    @Id @GeneratedValue
    @Column(name = "education_id")
    private Long id;
    private String schoolName;
    private String major;
    private LocalDate entranceDate;
    private LocalDate graduationDate;
    private double score;
    private String degree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public static Education createEducation(String schoolName, String major,
                LocalDate entranceDate, LocalDate graduationDate, double score, String degree) {
        // TODO - validate
        // - entranceDate, graduationDate 비교

        Education education = Education.builder()
                .schoolName(schoolName)
                .major(major)
                .entranceDate(entranceDate)
                .graduationDate(graduationDate)
                .score(score)
                .degree(degree)
                .build();
        return education;
    }

    @Builder
    public Education(Long id, String schoolName, String major, LocalDate entranceDate, LocalDate graduationDate, double score, String degree, Resume resume) {
        this.id = id;
        this.schoolName = schoolName;
        this.major = major;
        this.entranceDate = entranceDate;
        this.graduationDate = graduationDate;
        this.score = score;
        this.degree = degree;
        this.resume = resume;
    }
}
