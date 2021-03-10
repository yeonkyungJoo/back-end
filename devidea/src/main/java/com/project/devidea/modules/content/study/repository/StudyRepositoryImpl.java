package com.project.devidea.modules.content.study.repository;
import com.project.devidea.infra.config.modelmapper.CustomModelMapper;
import com.project.devidea.modules.content.study.QStudy;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.form.StudyListForm;
import com.project.devidea.modules.content.study.form.StudyRequestForm;
import com.project.devidea.modules.tag.QTag;
import com.project.devidea.modules.zone.QZone;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
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
                        , StudySearchConditions.eqLike(request.isLike())
                        , StudySearchConditions.eqRecruiting(request.isRecruiting())
                        , StudySearchConditions.eqZone(request.getZone())
//                        , StudySearchConditions.ltStudyId(request.getPage())
                        )
                .orderBy(qStudy.id.desc())
                .limit(request.getPageSize())
                .fetch();

        studyList.stream().forEach(study -> {
            result.add(studyMapper.map(study,StudyListForm.class));
        });
        return result;
    }
}
