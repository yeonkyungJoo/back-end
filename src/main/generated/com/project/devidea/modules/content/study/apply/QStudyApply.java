package com.project.devidea.modules.content.study.apply;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyApply is a Querydsl query type for StudyApply
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudyApply extends EntityPathBase<StudyApply> {

    private static final long serialVersionUID = 1303073226L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyApply studyApply = new QStudyApply("studyApply");

    public final BooleanPath accpted = createBoolean("accpted");

    public final StringPath answer = createString("answer");

    public final com.project.devidea.modules.account.QAccount applicant;

    public final DateTimePath<java.time.LocalDateTime> CreationDateTime = createDateTime("CreationDateTime", java.time.LocalDateTime.class);

    public final StringPath etc = createString("etc");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.project.devidea.modules.content.study.QStudy study;

    public QStudyApply(String variable) {
        this(StudyApply.class, forVariable(variable), INITS);
    }

    public QStudyApply(Path<? extends StudyApply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyApply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyApply(PathMetadata metadata, PathInits inits) {
        this(StudyApply.class, metadata, inits);
    }

    public QStudyApply(Class<? extends StudyApply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.applicant = inits.isInitialized("applicant") ? new com.project.devidea.modules.account.QAccount(forProperty("applicant")) : null;
        this.study = inits.isInitialized("study") ? new com.project.devidea.modules.content.study.QStudy(forProperty("study"), inits.get("study")) : null;
    }

}

