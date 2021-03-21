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
public class Activity {

    @Id @GeneratedValue
    @Column(name = "activity_id")
    private Long id;
    private String activityName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public static Activity createActivity(String activityName, LocalDateTime startDate, LocalDateTime endDate,
                                          String description, String link) {
        // TODO - validate
        // - startDate, endDate 비교

        Activity activity = Activity.builder()
                .activityName(activityName)
                .startDate(startDate)
                .endDate(endDate)
                .description(description)
                .link(link)
                .build();
        return activity;
    }
}
