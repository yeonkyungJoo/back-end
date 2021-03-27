package com.project.devidea.modules.tagzone.tag;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.QueryHint;
import java.util.List;

import static org.hibernate.jpa.QueryHints.HINT_CACHEABLE;
import static org.hibernate.jpa.QueryHints.HINT_CACHE_MODE;

@Transactional(readOnly = true)
public interface TagRepository extends JpaRepository<Tag,Long> {

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true")})
    @EntityGraph(attributePaths = {"parent", "children"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Tag> findAll();
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true")})
    @EntityGraph(attributePaths = {"parent", "children"}, type = EntityGraph.EntityGraphType.LOAD)
    Tag findByFirstName(String firstname);

    List<Tag> findByFirstNameIn(List<String> firstNames);
}
