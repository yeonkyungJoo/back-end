package com.project.devidea.modules.content.study.repository;
import com.project.devidea.modules.content.study.QStudy;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.form.StudyListForm;
import com.project.devidea.modules.content.study.form.StudyRequestForm;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyRepositoryImpl implements StudyRepositoryCustom {
    @Qualifier("StudyMapper")
    private final ModelMapper studyMapper;
    private final JPAQueryFactory query;

    @Override
    public List<StudyListForm> findByCondition(StudyRequestForm request) {
        List<StudyListForm> result=new ArrayList<>();
        QStudy qStudy = QStudy.study;
        List<Study> studyList= query.selectFrom(qStudy)
                .where(StudySearchConditions.eqKeyword(request.getKeyword())
                            ,StudySearchConditions.eqTags(request.getTags())
//                        , StudySearchConditions.eqLike(request.isLike())
                        , StudySearchConditions.eqLocalNameOfCity(request.getLocalNameOfCity())
                        , StudySearchConditions.eqProvince(request.getProvince())
                        , StudySearchConditions.eqRecruiting(request.getRecruiting())
                        )
                .orderBy(qStudy.id.desc())
                .limit(request.getPageSize())
//                .offset(request.getPage())
                .fetch();

        studyList.stream().forEach(study -> {
            result.add(studyMapper.map(study,StudyListForm.class));
        });
        return result;
    }
}
