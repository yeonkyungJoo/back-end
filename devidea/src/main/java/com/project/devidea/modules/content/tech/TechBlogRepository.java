package com.project.devidea.modules.content.tech;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Set;

public interface TechBlogRepository extends MongoRepository<TechBlog, ObjectId> {

    List<TechBlog> findAll();


    boolean existsByTitleAndContent(String title, String content);
    TechBlog findByTitle(String title);
}
