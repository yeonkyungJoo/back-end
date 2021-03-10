package com.project.devidea.modules.zone;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QZone is a Querydsl query type for Zone
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QZone extends EntityPathBase<Zone> {

    private static final long serialVersionUID = -1803937149L;

    public static final QZone zone = new QZone("zone");

    public final StringPath city = createString("city");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath localNameOfCity = createString("localNameOfCity");

    public final StringPath province = createString("province");

    public QZone(String variable) {
        super(Zone.class, forVariable(variable));
    }

    public QZone(Path<? extends Zone> path) {
        super(path.getType(), path.getMetadata());
    }

    public QZone(PathMetadata metadata) {
        super(Zone.class, metadata);
    }

}

