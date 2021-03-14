package com.project.devidea.modules.content.study.repository;
import com.project.devidea.modules.account.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.devidea.modules.content.study.Study;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface StudyRepository extends JpaRepository<Study, Long>, StudyRepositoryCustom{
    @EntityGraph(attributePaths = {"location", "tags"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Study> findStudyOrderByLikes();
    @EntityGraph(attributePaths = {"tags", "zones", "managers", "members"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Study> findById(Long id);

    @EntityGraph(attributePaths = {"location", "tags"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Study> findAll();
    //공개 스터디중 최신순으로

    @EntityGraph(attributePaths = "admin")
    Study findStudyByAdmin(Account admin);


}

