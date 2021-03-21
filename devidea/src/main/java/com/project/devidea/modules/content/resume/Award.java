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
public class Award {

    @Id @GeneratedValue
    @Column(name = "award_id")
    private Long id;
    private String name;
    private LocalDateTime date;
    private String link;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public static Award createAward(String name, LocalDateTime date,
                                        String link, String description) {
        // TODO - validate

        Award award = Award.builder()
                .name(name)
                .date(date)
                .link(link)
                .description(description)
                .build();
        return award;
    }
}
