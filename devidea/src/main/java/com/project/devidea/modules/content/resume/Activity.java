package com.project.devidea.modules.content.resume;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
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
}
