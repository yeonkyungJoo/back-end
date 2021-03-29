package com.project.devidea.modules.content.resume;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
