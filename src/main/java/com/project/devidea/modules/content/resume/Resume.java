package com.project.devidea.modules.content.resume;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Resume {

    @Id @GeneratedValue
    @Column(name = "resume_id")
    private Long id;
}
