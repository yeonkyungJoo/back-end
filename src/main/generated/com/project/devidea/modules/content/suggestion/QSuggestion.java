package com.project.devidea.modules.content.suggestion;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSuggestion is a Querydsl query type for Suggestion
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSuggestion extends EntityPathBase<Suggestion> {

    private static final long serialVersionUID = -79982130L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSuggestion suggestion = new QSuggestion("suggestion");

    public final DateTimePath<java.time.LocalDateTime> dateTime = createDateTime("dateTime", java.time.LocalDateTime.class);

    public final com.project.devidea.modules.account.QAccount from;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    public final StringPath subject = createString("subject");

    public final com.project.devidea.modules.account.QAccount to;

    public QSuggestion(String variable) {
        this(Suggestion.class, forVariable(variable), INITS);
    }

    public QSuggestion(Path<? extends Suggestion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSuggestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSuggestion(PathMetadata metadata, PathInits inits) {
        this(Suggestion.class, metadata, inits);
    }

    public QSuggestion(Class<? extends Suggestion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.from = inits.isInitialized("from") ? new com.project.devidea.modules.account.QAccount(forProperty("from")) : null;
        this.to = inits.isInitialized("to") ? new com.project.devidea.modules.account.QAccount(forProperty("to")) : null;
    }

}

