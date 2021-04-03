package com.project.devidea.modules.content.resume.project;

import com.project.devidea.modules.content.resume.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
