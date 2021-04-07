package com.project.devidea.modules.content.resume.award;

import com.project.devidea.modules.content.resume.Resume;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
        Award award = new Award();
        award.setResume(resume);
        award.setName(name);
        award.setDate(date);
        award.setLink(link);
        award.setDescription(description);

        resume.addAward(award);
        return award;
    }

}
