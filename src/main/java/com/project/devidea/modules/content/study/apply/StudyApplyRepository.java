package com.project.devidea.modules.content.study.apply;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.Study;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface StudyApplyRepository extends JpaRepository<StudyApply,Long> {
    @EntityGraph(attributePaths = {"applicant", "study"}, type = EntityGraph.EntityGraphType.LOAD)
    List<StudyApply> findAll();
    @EntityGraph(attributePaths = {"applicant", "study"}, type = EntityGraph.EntityGraphType.LOAD)
    StudyApply findByApplicantAndStudy(Account applicant, Study study);
    @EntityGraph(attributePaths = {"study"}, type = EntityGraph.EntityGraphType.LOAD)
    List<StudyApply> findByApplicant(Account account);

    @EntityGraph(attributePaths = {"study"}, type = EntityGraph.EntityGraphType.LOAD)
    List<StudyApply> findByApplicant_Nickname(String NickName);

    @EntityGraph(attributePaths = {"applicant"}, type = EntityGraph.EntityGraphType.LOAD)
    List<StudyApply> findByStudy(Study study);
    @EntityGraph(attributePaths = {"applicant"}, type = EntityGraph.EntityGraphType.LOAD)
    List<StudyApply> findByStudy_Id(Long id);
}
