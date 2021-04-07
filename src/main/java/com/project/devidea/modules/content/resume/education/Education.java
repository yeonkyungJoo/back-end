package com.project.devidea.modules.content.resume.education;

import com.project.devidea.modules.content.resume.Resume;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public static Education createEducation(Resume resume, String schoolName, String major,
                LocalDate entranceDate, LocalDate graduationDate, double score, String degree) {

        Education education = new Education();
        education.setResume(resume);
        education.setSchoolName(schoolName);
        education.setMajor(major);
        education.setEntranceDate(entranceDate);
        education.setGraduationDate(graduationDate);
        education.setScore(score);
        education.setDegree(degree);

        resume.addEducation(education);
        return education;
    }

}
