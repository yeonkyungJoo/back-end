package com.project.devidea.modules.account;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    Long id;

}
