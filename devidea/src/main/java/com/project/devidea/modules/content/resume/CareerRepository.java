package com.project.devidea.modules.content.resume;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface CareerRepository extends JpaRepository<Career, Long> {

    Optional<Career> findById(Long id);
}
