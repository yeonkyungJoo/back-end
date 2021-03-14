package com.project.devidea.modules.content.study.apply;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.Study;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface StudyApplyRepository extends JpaRepository<StudyApply,Long> {
    @EntityGraph(attributePaths = {"account", "study"}, type = EntityGraph.EntityGraphType.LOAD)
    List<StudyApply> findAll();
    @EntityGraph(attributePaths = {"account", "study"}, type = EntityGraph.EntityGraphType.LOAD)
    StudyApply findByAccountAndStudy(Account account,Study study);
    @EntityGraph(attributePaths = {"study"}, type = EntityGraph.EntityGraphType.LOAD)
    List<StudyApply> findByAccount(Account account);
    @EntityGraph(attributePaths = {"account"}, type = EntityGraph.EntityGraphType.LOAD)
    List<StudyApply> findByStudy(Study study);
    @EntityGraph(attributePaths = {"account"}, type = EntityGraph.EntityGraphType.LOAD)
    List<StudyApply> findByStudy_Id(Long id);
}
