package com.project.devidea.modules.content;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QContent is a Querydsl query type for Content
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QContent extends EntityPathBase<Content> {

    private static final long serialVersionUID = 1652927961L;

    public static final QContent content = new QContent("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QContent(String variable) {
        super(Content.class, forVariable(variable));
    }

    public QContent(Path<? extends Content> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContent(PathMetadata metadata) {
        super(Content.class, metadata);
    }

}

