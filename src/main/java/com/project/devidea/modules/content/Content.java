package com.project.devidea.modules.content;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(of = "id")
public class Content {
    @Id
    @GeneratedValue
    private Long id;
}
