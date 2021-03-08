package com.project.devidea.modules.content;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Entity
//@MappedSuperclass
@Getter
public class Content {
    @Id
    @GeneratedValue
    private Long id;

}
