package com.project.devidea.modules.tagzone.tag;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@NaturalIdCache
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_ID")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String firstName;

    @Column(unique = true)
    private String secondName;

    @Column(unique = true)
    private String thirdName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Tag parent;

    @Builder.Default
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "parent")
    private List<Tag> children = new ArrayList<>();

    //연관메서드
    public void addChild(Tag child) {
        this.children.add(child);
        child.parent = this;
    }
    //편의 메서드
    public Boolean contains(String name) {
        return firstName.equals(name) ||
                (secondName != null &&
                        secondName.equals(name)) ||
                (thirdName != null &&
                        thirdName.equals(name));

    }

    @Override
    public String toString() {
        return firstName;
    }
}
