package com.project.devidea.modules.content.study.repository;

import com.project.devidea.modules.content.study.Study;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class StudyRepositoryCustomImpl extends QuerydslRepositorySupport implements StudyRepositoryCustom {

    public StudyRepositoryCustomImpl(Class<?> domainClass) {
        super(Study.class);
    }
}
