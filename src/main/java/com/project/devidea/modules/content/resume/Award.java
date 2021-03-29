package com.project.devidea.modules.content.resume;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Award {

    @Id @GeneratedValue
    @Column(name = "award_id")
    private Long id;
    private String name;
    private LocalDate date;
    private String link;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public static Award createAward(Resume resume, String name, LocalDate date,
                                        String link, String description) {
        // TODO - validate

        Award award = new Award();
        award.setResume(resume);
        award.setName(name);
        award.setDate(date);
        award.setLink(link);
        award.setDescription(description);

        // TODO - why?
        resume.addAward(award);
        return award;
    }

    @Builder
    public Award(Long id, String name, LocalDate date, String link, String description, Resume resume) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.link = link;
        this.description = description;
        this.resume = resume;
    }
}
