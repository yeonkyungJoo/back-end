package com.project.devidea.modules.content.mentoring;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMentee is a Querydsl query type for Mentee
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMentee extends EntityPathBase<Mentee> {

    private static final long serialVersionUID = -2096296149L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMentee mentee = new QMentee("mentee");

    public final com.project.devidea.modules.account.QAccount account;

    public final StringPath description = createString("description");

    public final BooleanPath free = createBoolean("free");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath open = createBoolean("open");

    public final DateTimePath<java.time.LocalDateTime> publishedDate = createDateTime("publishedDate", java.time.LocalDateTime.class);

    public final SetPath<com.project.devidea.modules.tagzone.tag.Tag, com.project.devidea.modules.tagzone.tag.QTag> tags = this.<com.project.devidea.modules.tagzone.tag.Tag, com.project.devidea.modules.tagzone.tag.QTag>createSet("tags", com.project.devidea.modules.tagzone.tag.Tag.class, com.project.devidea.modules.tagzone.tag.QTag.class, PathInits.DIRECT2);

    public final SetPath<com.project.devidea.modules.tagzone.zone.Zone, com.project.devidea.modules.tagzone.zone.QZone> zones = this.<com.project.devidea.modules.tagzone.zone.Zone, com.project.devidea.modules.tagzone.zone.QZone>createSet("zones", com.project.devidea.modules.tagzone.zone.Zone.class, com.project.devidea.modules.tagzone.zone.QZone.class, PathInits.DIRECT2);

    public QMentee(String variable) {
        this(Mentee.class, forVariable(variable), INITS);
    }

    public QMentee(Path<? extends Mentee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMentee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMentee(PathMetadata metadata, PathInits inits) {
        this(Mentee.class, metadata, inits);
    }

    public QMentee(Class<? extends Mentee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.project.devidea.modules.account.QAccount(forProperty("account")) : null;
    }

}

