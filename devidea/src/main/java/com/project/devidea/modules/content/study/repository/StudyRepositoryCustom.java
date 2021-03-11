package com.project.devidea.modules.content.study.repository;

import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.form.StudyListForm;
import com.project.devidea.modules.content.study.form.StudyRequestForm;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudyRepositoryCustom {

   @EntityGraph(attributePaths = {"tags", "location"})
    List<StudyListForm> findByCondition(StudyRequestForm searchCondition);
}
