package com.project.devidea.modules.content;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(of = "id")
public class Content {
    @Id
    @GeneratedValue
    private Long id;
}
