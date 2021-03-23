package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.tagzone.tag.Tag;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Career {

    @Id @GeneratedValue
    @Column(name = "career_id")
    private Long id;
    private String companyName;
    private String duty;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean present;    // 재직중

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "career_tag",
                joinColumns = @JoinColumn(name = "career_id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    private String detail;
    private String url;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    public static Career createCareer(Resume resume, String companyName, String duty,
                  LocalDate startDate, LocalDate endDate, boolean present, Set<Tag> tags, String detail, String url) {

        // TODO - validate
        // - startDate와 endDate 비교
        // - endDate와 now 비교
        // - endDate, present 비교

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

    @Builder
    public Career(Long id, String companyName, String duty, LocalDate startDate, LocalDate endDate, boolean present, Set<Tag> tags, String detail, String url, Resume resume) {
        this.id = id;
        this.companyName = companyName;
        this.duty = duty;
        this.startDate = startDate;
        this.endDate = endDate;
        this.present = present;
        this.tags = tags;
        this.detail = detail;
        this.url = url;
        this.resume = resume;
    }
}
