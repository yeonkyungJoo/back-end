package com.project.devidea.modules.content.study.repository;

import com.project.devidea.modules.content.study.QStudy;
import com.project.devidea.modules.tagzone.tag.QTag;
import com.project.devidea.modules.tagzone.zone.QZone;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class StudySearchConditions {
    public static QStudy qStudy=QStudy.study;
    private static QZone qZone=QZone.zone;
    private static QTag qTag=QTag.tag;

    public static BooleanExpression eqKeyword(String keyword) {
        if(keyword==null) return null;
        BooleanExpression zoneCondition = eqZone(keyword);
        BooleanExpression tagCondition = eqTag(keyword);
        BooleanExpression titleCondition = qStudy.title.containsIgnoreCase(keyword);
        return zoneCondition.or(tagCondition).or(titleCondition);
    }

    public static BooleanExpression eqZone(String zone) {
        if (zone == null) return null;
        return eqCity(zone).or(eqProvince(zone));
    }
    public static BooleanExpression eqTags(List<String> tags) {
        if(tags==null) return null;
        BooleanExpression tagCondition;
        for(String tag:tags){
            tagCondition=eqTag(tag);
            if(tagCondition.isTrue()== Expressions.asBoolean(true).isTrue()){
                return tagCondition;
            }
        }
        return Expressions.asBoolean(false).isFalse();
    }
    public static BooleanExpression eqTag(String tag) {
        if (tag == null) return null;
        return qStudy.tags.any().
                firstName.containsIgnoreCase(tag)
                .or(qStudy.tags.any()
                        .secondName.containsIgnoreCase(tag))
                .or(qStudy.tags.any()
                        .thirdName.containsIgnoreCase(tag));
    }
    public static BooleanExpression eqCity(String city) {
        return city!=null?qStudy.location.city.containsIgnoreCase(city):null;
    }
    public static BooleanExpression eqProvince(String province) {
        return province!=null?qStudy.location.city.containsIgnoreCase(province):null;
    }
    private static BooleanExpression eqMentorRecruiting(Boolean mentorRecruiting) {
        return mentorRecruiting != null ? qStudy.mentoRecruiting.eq(mentorRecruiting) : null;
    }

    public static BooleanExpression eqRecruiting(Boolean recruiting) {
        return recruiting != null ? qStudy.recruiting.eq(recruiting) : null;
    }

//    private static List<StudySpecifier> getOrderSpecifier(Sort sort) {
//        OrderSpecifier<?> sortedColumn = QuerydslUtil.getSortedColumn(Order.DESC, qRole);
//    }

}
