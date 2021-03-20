package com.project.devidea.modules.content.study.repository;

import com.project.devidea.modules.content.study.QStudy;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.form.StudyListForm;
import com.project.devidea.modules.content.study.form.StudySearchForm;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyRepositoryImpl implements StudyRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public List<Study> findByCondition(StudySearchForm request) {
        List<StudyListForm> result = new ArrayList<>();
        QStudy qStudy = QStudy.study;
        List<Study> studyList = query.selectFrom(qStudy)
                .where(StudySearchConditions.eqKeyword(request.getKeyword())
                        , StudySearchConditions.eqTags(request.getTags())
                        , StudySearchConditions.eqCity(request.getCity())
                        , StudySearchConditions.eqProvince(request.getProvince())
                        , StudySearchConditions.eqRecruiting(request.getRecruiting())
                        , StudySearchConditions.eqMentoRecruting(request.getMentorRecruiting())
                )
                .orderBy(qStudy.id.desc())
                .limit(request.getPageSize())
                .offset(request.getPage())
                .fetch();

        return studyList;
    }
//
//    @Override
//    public List<Study> findByMember(Account account) {
//        QStudy qStudy = QStudy.study;
//        QAccount qAccount = QAccount.account;
//        return query.selectFrom(qStudy).innerJoin(qStudy.members., qAccount)
//                .fetchJoin()
//                .fetch();
//    }
}
