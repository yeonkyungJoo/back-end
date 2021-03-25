package com.project.devidea.modules.content.study.repository;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.StudyMember;
import com.project.devidea.modules.content.study.Study_Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public interface StudyMemberRepository extends JpaRepository<StudyMember,Long> {

    @EntityGraph(attributePaths = {"study","member"}, type = EntityGraph.EntityGraphType.LOAD)
    List<StudyMember> findAll();
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.LOAD)
    List<StudyMember> findByStudy(Study study);

    @EntityGraph(attributePaths = {"study"}, type = EntityGraph.EntityGraphType.LOAD)
    List<StudyMember> findByMember(Account member);

    StudyMember findByStudyAndMember(Study study,Account member);

    StudyMember findByStudy_IdAndMember_Nickname(Long studyId,String nickName);
    List<StudyMember> findByStudy_Id(Long studyId);
    void deleteById(Long id);

    void deleteByStudy_IdAndMember_Id(Long studyId,Long memberId);

    @Modifying
    @Query("update StudyMember s set s.role =:studyRole where s.member.id=:accountId and s.study.id=:studyId ")
    void updateRole(@Param("studyId") Long studyId, @Param("accountId")Long accountId,@Param("studyRole") Study_Role studyRole);

}
