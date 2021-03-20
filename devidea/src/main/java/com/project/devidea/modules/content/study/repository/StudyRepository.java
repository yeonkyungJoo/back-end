package com.project.devidea.modules.content.study.repository;
import com.project.devidea.modules.account.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.devidea.modules.content.study.Study;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface StudyRepository extends JpaRepository<Study, Long>, StudyRepositoryCustom{
    @EntityGraph(attributePaths = {"tags", "location", "members"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Study> findById(Long id);

    @EntityGraph(attributePaths = {"location", "tags","members"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Study> findAll();

    //스터디 좋아요 표시
    @Modifying
    @Query("update Study s set s.Likes =s.Likes+1 where s.id=:studyId")
    void LikeStudyById(@Param("studyId") Long studyId);

    //스터디 떠났을떄
    @Modifying
    @Query("update Study s set s.counts =s.counts-1 where s.id=:studyId")
    void LeaveStudy(@Param("studyId") Long studyId);
    
    //스터디 추가 됐을때
    @Modifying
    @Query("update Study s set s.counts =s.counts+1 where s.id=:studyId")
    void addStudy(@Param("studyId") Long studyId);



}

