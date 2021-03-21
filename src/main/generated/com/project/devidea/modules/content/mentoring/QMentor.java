package com.project.devidea.modules.content.mentoring;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMentor is a Querydsl query type for Mentor
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMentor extends EntityPathBase<Mentor> {

    private static final long serialVersionUID = -2096295826L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMentor mentor = new QMentor("mentor");

    public final com.project.devidea.modules.account.QAccount account;

    public final NumberPath<Integer> cost = createNumber("cost", Integer.class);

    public final BooleanPath free = createBoolean("free");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath open = createBoolean("open");

    public final DateTimePath<java.time.LocalDateTime> publishedDate = createDateTime("publishedDate", java.time.LocalDateTime.class);

    public final com.project.devidea.modules.content.resume.QResume resume;

    public final SetPath<com.project.devidea.modules.tagzone.tag.Tag, com.project.devidea.modules.tagzone.tag.QTag> tags = this.<com.project.devidea.modules.tagzone.tag.Tag, com.project.devidea.modules.tagzone.tag.QTag>createSet("tags", com.project.devidea.modules.tagzone.tag.Tag.class, com.project.devidea.modules.tagzone.tag.QTag.class, PathInits.DIRECT2);

    public final SetPath<com.project.devidea.modules.tagzone.zone.Zone, com.project.devidea.modules.tagzone.zone.QZone> zones = this.<com.project.devidea.modules.tagzone.zone.Zone, com.project.devidea.modules.tagzone.zone.QZone>createSet("zones", com.project.devidea.modules.tagzone.zone.Zone.class, com.project.devidea.modules.tagzone.zone.QZone.class, PathInits.DIRECT2);

    public QMentor(String variable) {
        this(Mentor.class, forVariable(variable), INITS);
    }

    public QMentor(Path<? extends Mentor> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMentor(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMentor(PathMetadata metadata, PathInits inits) {
        this(Mentor.class, metadata, inits);
    }

    public QMentor(Class<? extends Mentor> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.project.devidea.modules.account.QAccount(forProperty("account")) : null;
        this.resume = inits.isInitialized("resume") ? new com.project.devidea.modules.content.resume.QResume(forProperty("resume"), inits.get("resume")) : null;
    }

}

