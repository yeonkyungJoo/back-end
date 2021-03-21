package com.project.devidea.modules.content.resume;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    Resume findByAccountId(Long accountId);
}
