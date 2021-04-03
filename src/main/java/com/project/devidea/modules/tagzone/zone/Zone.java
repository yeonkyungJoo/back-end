package com.project.devidea.modules.tagzone.zone;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Zone implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ZONE_ID")
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String province;

    @Override
    public String toString() {
        return String.format("%s/%s", city, province);
    }
}
