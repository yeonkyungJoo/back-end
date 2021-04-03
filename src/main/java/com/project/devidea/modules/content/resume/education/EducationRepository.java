package com.project.devidea.modules.content.resume.education;

import com.project.devidea.modules.content.resume.education.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface EducationRepository extends JpaRepository<Education, Long> {
}
