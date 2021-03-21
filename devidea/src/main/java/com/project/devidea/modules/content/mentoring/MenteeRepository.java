package com.project.devidea.modules.content.mentoring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MenteeRepository extends JpaRepository<Mentee, Long> {

    Mentee findByAccountId(Long accountId);
}
