package com.project.devidea.modules.content.resume;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCareer is a Querydsl query type for Career
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCareer extends EntityPathBase<Career> {

    private static final long serialVersionUID = 1845261823L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCareer career = new QCareer("career");

    public final StringPath companyName = createString("companyName");

    public final StringPath detail = createString("detail");

    public final StringPath duty = createString("duty");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath present = createBoolean("present");

    public final QResume resume;

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final SetPath<com.project.devidea.modules.tagzone.tag.Tag, com.project.devidea.modules.tagzone.tag.QTag> tags = this.<com.project.devidea.modules.tagzone.tag.Tag, com.project.devidea.modules.tagzone.tag.QTag>createSet("tags", com.project.devidea.modules.tagzone.tag.Tag.class, com.project.devidea.modules.tagzone.tag.QTag.class, PathInits.DIRECT2);

    public final StringPath url = createString("url");

    public QCareer(String variable) {
        this(Career.class, forVariable(variable), INITS);
    }

    public QCareer(Path<? extends Career> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCareer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCareer(PathMetadata metadata, PathInits inits) {
        this(Career.class, metadata, inits);
    }

    public QCareer(Class<? extends Career> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.resume = inits.isInitialized("resume") ? new QResume(forProperty("resume"), inits.get("resume")) : null;
    }

}

