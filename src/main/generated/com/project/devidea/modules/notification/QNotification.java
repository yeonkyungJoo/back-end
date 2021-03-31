package com.project.devidea.modules.notification;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotification is a Querydsl query type for Notification
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNotification extends EntityPathBase<Notification> {

    private static final long serialVersionUID = -1937470237L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNotification notification = new QNotification("notification");

    public final com.project.devidea.modules.account.QAccount account;

    public final BooleanPath checked = createBoolean("checked");

    public final DateTimePath<java.time.LocalDateTime> checkedDateTime = createDateTime("checkedDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdDateTime = createDateTime("createdDateTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath link = createString("link");

    public final StringPath message = createString("message");

    public final EnumPath<NotificationType> notificationType = createEnum("notificationType", NotificationType.class);

    public final StringPath title = createString("title");

    public QNotification(String variable) {
        this(Notification.class, forVariable(variable), INITS);
    }

    public QNotification(Path<? extends Notification> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNotification(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNotification(PathMetadata metadata, PathInits inits) {
        this(Notification.class, metadata, inits);
    }

    public QNotification(Class<? extends Notification> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.project.devidea.modules.account.QAccount(forProperty("account")) : null;
    }

}

