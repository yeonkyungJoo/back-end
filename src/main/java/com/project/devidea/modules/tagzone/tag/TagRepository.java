package com.project.devidea.modules.tagzone.tag;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface TagRepository extends JpaRepository<Tag,Long> {
    @EntityGraph(attributePaths = {"parent", "children"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Tag> findAll();
    @EntityGraph(attributePaths = {"parent", "children"}, type = EntityGraph.EntityGraphType.LOAD)
    Tag findByFirstName(String firstname);
}
