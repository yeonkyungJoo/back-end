package com.project.devidea.modules.content.resume;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public static Education createEducation(String schoolName, String major,
                        LocalDateTime entranceDate, LocalDateTime graduationDate, double score, String degree) {
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

}
