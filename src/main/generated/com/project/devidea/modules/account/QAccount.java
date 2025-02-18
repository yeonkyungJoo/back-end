package com.project.devidea.modules.account;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = -2118687423L;

    public static final QAccount account = new QAccount("account");

    public final StringPath bio = createString("bio");

    public final NumberPath<Integer> careerYears = createNumber("careerYears", Integer.class);

    public final StringPath email = createString("email");

    public final StringPath emailCheckToken = createString("emailCheckToken");

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<Interest, QInterest> interests = this.<Interest, QInterest>createSet("interests", Interest.class, QInterest.class, PathInits.DIRECT2);

    public final StringPath job = createString("job");

    public final DateTimePath<java.time.LocalDateTime> joinedAt = createDateTime("joinedAt", java.time.LocalDateTime.class);

    public final SetPath<MainActivityZone, QMainActivityZone> mainActivityZones = this.<MainActivityZone, QMainActivityZone>createSet("mainActivityZones", MainActivityZone.class, QMainActivityZone.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath profileImage = createString("profileImage");

    public final StringPath provider = createString("provider");

    public final BooleanPath receiveEmail = createBoolean("receiveEmail");

    public final BooleanPath receiveMentoringNotification = createBoolean("receiveMentoringNotification");

    public final BooleanPath receiveNotification = createBoolean("receiveNotification");

    public final BooleanPath receiveRecruitingNotification = createBoolean("receiveRecruitingNotification");

    public final BooleanPath receiveStudyNotification = createBoolean("receiveStudyNotification");

    public final BooleanPath receiveTechNewsNotification = createBoolean("receiveTechNewsNotification");

    public final StringPath roles = createString("roles");

    public final SetPath<com.project.devidea.modules.content.study.StudyMember, com.project.devidea.modules.content.study.QStudyMember> studies = this.<com.project.devidea.modules.content.study.StudyMember, com.project.devidea.modules.content.study.QStudyMember>createSet("studies", com.project.devidea.modules.content.study.StudyMember.class, com.project.devidea.modules.content.study.QStudyMember.class, PathInits.DIRECT2);

    public final StringPath techStacks = createString("techStacks");

    public final StringPath url = createString("url");

    public QAccount(String variable) {
        super(Account.class, forVariable(variable));
    }

    public QAccount(Path<? extends Account> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccount(PathMetadata metadata) {
        super(Account.class, metadata);
    }

}

