package com.project.devidea.modules.zone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Zone {

    @Id
    @GeneratedValue
    private Long id;
}
