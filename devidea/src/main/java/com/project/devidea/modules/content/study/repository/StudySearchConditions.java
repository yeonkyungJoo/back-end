package com.project.devidea.modules.content.study.repository;

import com.project.devidea.modules.content.study.QStudy;
import com.project.devidea.modules.tag.QTag;
import com.project.devidea.modules.zone.QZone;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class StudySearchConditions {
    public static QStudy qStudy=QStudy.study;
    private static QZone qZone=QZone.zone;
    private static QTag qTag=QTag.tag;

    public static BooleanExpression eqKeyword(String keyword) {
        if (keyword == null) return null;
        BooleanExpression zoneCondition = eqZone(keyword);
        BooleanExpression tagCondition = qStudy.tags.any()
                .firstName.containsIgnoreCase(keyword)
                .or(qStudy.tags.any()
                        .secondName.containsIgnoreCase(keyword))
                .or(qStudy.tags.any()
                        .thirdName.containsIgnoreCase(keyword));
        BooleanExpression titleCondition = qStudy.title.containsIgnoreCase(keyword);
        return zoneCondition.or(tagCondition).or(titleCondition);
    }

    public static BooleanExpression eqZone(String zone) {
        if (zone == null) return null;
        BooleanExpression condition1 = qZone.localNameOfCity.eq(zone);
        BooleanExpression condition2 = qZone.province.eq(zone);
        BooleanExpression condition3 = qZone.city.eq(zone);
        return condition1.or(condition2).or(condition3);
    }

    private static BooleanExpression eqMentorRecruiting(Boolean mentorRecruiting) {
        return mentorRecruiting != null ? qStudy.mentoRecruiting.eq(mentorRecruiting) : null;
    }

    public static BooleanExpression eqRecruiting(Boolean recruiting) {
        return recruiting != null ? qStudy.recruiting.eq(recruiting) : null;
    }

    public static BooleanExpression eqLike(Boolean like) {
        return like != null ? qStudy.recruiting.eq(like) : null;
    }

    public static BooleanExpression ltStudyId(Long studyId) {
        if (studyId == 0) {
            return null; // BooleanExpression 자리에 0이 반환되면 조건문에서 자동으로 제거된다
        }

        return qStudy.id.lt(studyId);
    }
}
