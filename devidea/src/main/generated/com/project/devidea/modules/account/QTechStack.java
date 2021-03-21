package com.project.devidea.modules.account;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTechStack is a Querydsl query type for TechStack
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTechStack extends EntityPathBase<TechStack> {

    private static final long serialVersionUID = -1266998362L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTechStack techStack = new QTechStack("techStack");

    public final QAccount account;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.project.devidea.modules.tagzone.tag.QTag tag;

    public QTechStack(String variable) {
        this(TechStack.class, forVariable(variable), INITS);
    }

    public QTechStack(Path<? extends TechStack> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTechStack(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTechStack(PathMetadata metadata, PathInits inits) {
        this(TechStack.class, metadata, inits);
    }

    public QTechStack(Class<? extends TechStack> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account"), inits.get("account")) : null;
        this.tag = inits.isInitialized("tag") ? new com.project.devidea.modules.tagzone.tag.QTag(forProperty("tag"), inits.get("tag")) : null;
    }

}

