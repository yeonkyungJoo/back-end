package com.project.devidea.modules.tag;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Tag {
    @Id
    @GeneratedValue
    private Long id;

}
