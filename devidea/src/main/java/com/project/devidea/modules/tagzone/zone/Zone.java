package com.project.devidea.modules.tagzone.zone;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"city", "province"}))
public class Zone {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String province;

    @Override
    public String toString() {
        return String.format("%s(%s)/%s", city, province);
    }
}
