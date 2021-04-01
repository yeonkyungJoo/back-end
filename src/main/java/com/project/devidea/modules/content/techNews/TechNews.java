package com.project.devidea.modules.content.techNews;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Index;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@Document(collection = "TechBlog")
@ToString
public class TechNews {

    @Id
    private String id;
    @Indexed
    private TechSite techSite;
    private String url;
    private String title;
    private String content;
    private String imgUrl;
    private Set<String> tags;
    private String writerName;
    private String writerImgUrl;
    private LocalDate createdDate;

}
