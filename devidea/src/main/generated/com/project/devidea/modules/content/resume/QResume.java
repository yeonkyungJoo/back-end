package com.project.devidea.modules.content.resume;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QResume is a Querydsl query type for Resume
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QResume extends EntityPathBase<Resume> {

    private static final long serialVersionUID = -2016528722L;

    public static final QResume resume = new QResume("resume");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QResume(String variable) {
        super(Resume.class, forVariable(variable));
    }

    public QResume(Path<? extends Resume> path) {
        super(path.getType(), path.getMetadata());
    }

    public QResume(PathMetadata metadata) {
        super(Resume.class, metadata);
    }

}

