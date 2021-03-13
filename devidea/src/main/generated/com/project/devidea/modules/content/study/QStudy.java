package com.project.devidea.modules.content.study;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudy is a Querydsl query type for Study
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudy extends EntityPathBase<Study> {

    private static final long serialVersionUID = 488767172L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudy study = new QStudy("study");

    public final com.project.devidea.modules.account.QAccount admin;

    public final NumberPath<Integer> counts = createNumber("counts", Integer.class);

    public final StringPath fullDescription = createString("fullDescription");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<Level> level = createEnum("level", Level.class);

    public final NumberPath<Integer> Likes = createNumber("Likes", Integer.class);

    public final com.project.devidea.modules.tagzone.zone.QZone location;

    public final NumberPath<Integer> maxCount = createNumber("maxCount", Integer.class);

    public final SetPath<com.project.devidea.modules.account.Account, com.project.devidea.modules.account.QAccount> members = this.<com.project.devidea.modules.account.Account, com.project.devidea.modules.account.QAccount>createSet("members", com.project.devidea.modules.account.Account.class, com.project.devidea.modules.account.QAccount.class, PathInits.DIRECT2);

    public final BooleanPath mentoRecruiting = createBoolean("mentoRecruiting");

    public final BooleanPath open = createBoolean("open");

    public final DateTimePath<java.time.LocalDateTime> publishedDateTime = createDateTime("publishedDateTime", java.time.LocalDateTime.class);

    public final StringPath question = createString("question");

    public final BooleanPath recruiting = createBoolean("recruiting");

    public final StringPath shortDescription = createString("shortDescription");

    public final SetPath<com.project.devidea.modules.tagzone.tag.Tag, com.project.devidea.modules.tagzone.tag.QTag> tags = this.<com.project.devidea.modules.tagzone.tag.Tag, com.project.devidea.modules.tagzone.tag.QTag>createSet("tags", com.project.devidea.modules.tagzone.tag.Tag.class, com.project.devidea.modules.tagzone.tag.QTag.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QStudy(String variable) {
        this(Study.class, forVariable(variable), INITS);
    }

    public QStudy(Path<? extends Study> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudy(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudy(PathMetadata metadata, PathInits inits) {
        this(Study.class, metadata, inits);
    }

    public QStudy(Class<? extends Study> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.admin = inits.isInitialized("admin") ? new com.project.devidea.modules.account.QAccount(forProperty("admin")) : null;
        this.location = inits.isInitialized("location") ? new com.project.devidea.modules.tagzone.zone.QZone(forProperty("location")) : null;
    }

}

