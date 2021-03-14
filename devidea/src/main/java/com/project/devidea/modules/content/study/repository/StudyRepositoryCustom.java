package com.project.devidea.modules.content.study.repository;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.form.StudyListForm;
import com.project.devidea.modules.content.study.form.StudySearchForm;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface StudyRepositoryCustom {

   @EntityGraph(attributePaths = {"tags", "location"})
    List<Study> findByCondition(StudySearchForm searchCondition);

   @EntityGraph(attributePaths = {"members"})
    List<Study> findByMember(Account account);
}
