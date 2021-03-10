package com.project.devidea.modules.content.study.repository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.devidea.modules.content.study.Study;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface StudyRepository extends JpaRepository<Study, Long> ,StudyRepositoryCustom{
    List<Study> findAll();

    //공개 스터디중 최신순으로
    @EntityGraph(attributePaths = {"zones", "tags"})
    List<Study> findStudyByOpenOrderByPublishedDateTimeDesc(Boolean Open);


}

