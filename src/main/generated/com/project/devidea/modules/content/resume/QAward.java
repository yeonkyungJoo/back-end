package com.project.devidea.modules.content.resume;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAward is a Querydsl query type for Award
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAward extends EntityPathBase<Award> {

    private static final long serialVersionUID = -218777668L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAward award = new QAward("award");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath link = createString("link");

    public final StringPath name = createString("name");

    public final QResume resume;

    public QAward(String variable) {
        this(Award.class, forVariable(variable), INITS);
    }

    public QAward(Path<? extends Award> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAward(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAward(PathMetadata metadata, PathInits inits) {
        this(Award.class, metadata, inits);
    }

    public QAward(Class<? extends Award> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.resume = inits.isInitialized("resume") ? new QResume(forProperty("resume"), inits.get("resume")) : null;
    }

}

