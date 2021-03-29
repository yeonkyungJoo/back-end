package com.project.devidea.modules.content.resume;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface EducationRepository extends JpaRepository<Education, Long> {
}
