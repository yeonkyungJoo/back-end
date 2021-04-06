package com.project.devidea.modules.content.resume.activity;

import com.project.devidea.modules.content.resume.activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
