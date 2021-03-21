package com.project.devidea.modules.content.study;

import com.project.devidea.modules.account.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(indexes=@Index(columnList = "study_id,member_id"))
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(of="id")
public class StudyMember {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="study_id")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Account member;

    @Setter
    @Enumerated(EnumType.STRING)
    private Study_Role role;

    private LocalDateTime JoinDate;
    @Override
    public String toString(){
         return member.toString();
    }



}