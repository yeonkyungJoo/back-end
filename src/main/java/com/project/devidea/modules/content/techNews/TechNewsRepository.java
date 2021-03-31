package com.project.devidea.modules.content.techNews;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TechNewsRepository extends MongoRepository<TechNews, String> {

    List<TechNews> findAllByTechSite(TechSite techSite);

    boolean existsByTitleAndContent(String title, String content);
    TechNews findByTitle(String title);
}
