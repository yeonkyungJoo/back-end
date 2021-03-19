package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.tagzone.tag.Tag;
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

}
