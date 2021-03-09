//package com.project.devidea.modules.content.study.repository;
//import org.springframework.data.jpa.repository.EntityGraph;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import com.project.devidea.modules.content.study.Study;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Repository
//@Transactional
//public interface StudyRepository extends JpaRepository<Study, Long> ,StudyRepositoryCustom{
//    List<Study> findAll();
//
//    //공개 스터디중 최신순으로
//    @EntityGraph(attributePaths = {"zones", "tags"})
//    List<Study> findStudyByOpenOrderByPublishedDateTimeDesc(Boolean Open);
////
////    //공개 스터디중 좋아요순으로
////    @EntityGraph(attributePaths = {"zones", "tags"})
////    List<Study> findStudyByOpenOrderByLikesTimeDesc(Boolean Open);
////
////    //인원 안차고 공개중인 스터디중 최신순으로
////    @EntityGraph(attributePaths = {"zones", "tags"})
////    List<Study> findStudyByOpenOrderByLikesTimeDesc(Boolean Open);
////
////    @EntityGraph(attributePaths = {"zones", "tags"})
////    List<Study> findStudyByOpenOrderByLikesTimeDesc(Boolean Open);
//}
//
