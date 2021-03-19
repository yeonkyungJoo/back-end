package com.project.devidea.modules.content.study.repository;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.StudyMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
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
    void deleteById(Long id);
    void deleteByStudyAndMember(Study study,Account member);
}
