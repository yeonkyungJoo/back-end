package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.tagzone.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Career {

    @Id @GeneratedValue
    @Column(name = "career_id")
    private Long id;
    private String companyName;
    private String duty;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean present;    // 재직중

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "career_tag",
                joinColumns = @JoinColumn(name = "career_id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    private String detail;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public static Career createCareer(String companyName, String duty,
              LocalDateTime startDate, LocalDateTime endDate, boolean present, Set<Tag> tags, String detail, String url) {

        // TODO - validate
        // - startDate와 endDate 비교
        // - endDate, present 비교

        Career career = Career.builder()
                .companyName(companyName)
                .duty(duty)
                .startDate(startDate)
                .endDate(endDate)
                .present(present)
                .tags(tags)
                .detail(detail)
                .url(url)
                .build();
        return career;
    }

}
