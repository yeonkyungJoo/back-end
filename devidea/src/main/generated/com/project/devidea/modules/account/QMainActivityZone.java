package com.project.devidea.modules.account;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMainActivityZone is a Querydsl query type for MainActivityZone
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMainActivityZone extends EntityPathBase<MainActivityZone> {

    private static final long serialVersionUID = -211397856L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMainActivityZone mainActivityZone = new QMainActivityZone("mainActivityZone");

    public final QAccount account;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.project.devidea.modules.tagzone.zone.QZone zone;

    public QMainActivityZone(String variable) {
        this(MainActivityZone.class, forVariable(variable), INITS);
    }

    public QMainActivityZone(Path<? extends MainActivityZone> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMainActivityZone(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMainActivityZone(PathMetadata metadata, PathInits inits) {
        this(MainActivityZone.class, metadata, inits);
    }

    public QMainActivityZone(Class<? extends MainActivityZone> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account"), inits.get("account")) : null;
        this.zone = inits.isInitialized("zone") ? new com.project.devidea.modules.tagzone.zone.QZone(forProperty("zone")) : null;
    }

}

