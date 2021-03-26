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
public class Activity {

    @Id @GeneratedValue
    @Column(name = "activity_id")
    private Long id;
    private String activityName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public static Activity createActivity(Resume resume, String activityName, LocalDate startDate, LocalDate endDate,
                                          String description, String link) {
        // TODO - validate
        // - startDate, endDate 비교

        Activity activity = new Activity();
        activity.setResume(resume);
        activity.setActivityName(activityName);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setDescription(description);
        activity.setLink(link);

        resume.addActivity(activity);
        return activity;
    }

    @Builder
    public Activity(Long id, String activityName, LocalDate startDate, LocalDate endDate, String description, String link, Resume resume) {
        this.id = id;
        this.activityName = activityName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.link = link;
        this.resume = resume;
    }
}
