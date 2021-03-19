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
public class Award {

    @Id @GeneratedValue
    @Column(name = "award_id")
    private Long id;
    private String name;
    private LocalDateTime date;
    private String link;
    private String description;

}
