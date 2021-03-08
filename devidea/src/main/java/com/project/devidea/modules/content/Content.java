package com.project.devidea.modules.content;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content {
    @Id
    @GeneratedValue
    Long id;
}
