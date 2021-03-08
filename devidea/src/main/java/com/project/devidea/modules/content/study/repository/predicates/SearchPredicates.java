//package com.project.devidea.modules.content.study.repository.predicates;
//
//import java.util.Set;
//import com.querydsl.core.types.Predicate;
//public class SearchPredicates {
//
//    public static Predicate findByTagsAndZones(Set<Tag> tags, Set<Zone> zones) {
//        QAccount account = QAccount.account;
//        return account.zones.any().in(zones).and(account.tags.any().in(tags));
//    }
//}
