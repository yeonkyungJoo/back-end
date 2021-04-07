package com.project.devidea.modules.content.resume.career;

import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.tagzone.tag.Tag;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Career {

    @Id @GeneratedValue
    @Column(name = "career_id")
    private Long id;
    private String companyName;
    private String duty;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean present;    // 재직중
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "career_tag",
                joinColumns = @JoinColumn(name = "career_id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    private String detail;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    public static Career createCareer(Resume resume, String companyName, String duty,
                  LocalDate startDate, LocalDate endDate, boolean present, Set<Tag> tags, String detail, String url) {

        Career career = new Career();
        career.setResume(resume);
        career.setCompanyName(companyName);
        career.setDuty(duty);
        career.setStartDate(startDate);
        career.setEndDate(endDate);
        career.setPresent(present);
        career.setTags(tags);
        career.setDetail(detail);
        career.setUrl(url);

        resume.addCareer(career);
        return career;
    }

}
