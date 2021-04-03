package com.project.devidea.modules.content.resume.award;

import com.project.devidea.modules.content.resume.award.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AwardRepository extends JpaRepository<Award, Long> {
}
