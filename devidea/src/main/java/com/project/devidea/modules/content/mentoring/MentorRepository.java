package com.project.devidea.modules.content.mentoring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MentorRepository extends JpaRepository<Mentor, Long> {

    Mentor findByAccountId(Long accountId);
}
