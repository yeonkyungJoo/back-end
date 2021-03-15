package com.project.devidea.modules.content.study.apply;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.Study;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table( indexes ={
        @Index(name = "account_index", columnList = "account_id"),
        @Index(name = "study_index", columnList = "study_id")})
@EqualsAndHashCode(of = "id")
public class StudyApply {

    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    Account account; //보내는 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    Study study; //스터디

    Boolean accpted;

    LocalDateTime CreationDateTime;

    String answer;

    String etc;

    public void setAccpted(boolean decision){
        accpted=decision;
    }
}
