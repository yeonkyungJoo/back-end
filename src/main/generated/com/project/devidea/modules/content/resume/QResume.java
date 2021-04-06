package com.project.devidea.modules.content.resume;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QResume is a Querydsl query type for Resume
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QResume extends EntityPathBase<Resume> {

    private static final long serialVersionUID = -2016528722L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QResume resume = new QResume("resume");

    public final com.project.devidea.modules.account.QAccount account;

    public final ListPath<com.project.devidea.modules.content.resume.activity.Activity, com.project.devidea.modules.content.resume.activity.QActivity> activites = this.<com.project.devidea.modules.content.resume.activity.Activity, com.project.devidea.modules.content.resume.activity.QActivity>createList("activites", com.project.devidea.modules.content.resume.activity.Activity.class, com.project.devidea.modules.content.resume.activity.QActivity.class, PathInits.DIRECT2);

    public final ListPath<com.project.devidea.modules.content.resume.award.Award, com.project.devidea.modules.content.resume.award.QAward> awards = this.<com.project.devidea.modules.content.resume.award.Award, com.project.devidea.modules.content.resume.award.QAward>createList("awards", com.project.devidea.modules.content.resume.award.Award.class, com.project.devidea.modules.content.resume.award.QAward.class, PathInits.DIRECT2);

    public final StringPath blog = createString("blog");

    public final ListPath<com.project.devidea.modules.content.resume.career.Career, com.project.devidea.modules.content.resume.career.QCareer> careers = this.<com.project.devidea.modules.content.resume.career.Career, com.project.devidea.modules.content.resume.career.QCareer>createList("careers", com.project.devidea.modules.content.resume.career.Career.class, com.project.devidea.modules.content.resume.career.QCareer.class, PathInits.DIRECT2);

    public final ListPath<com.project.devidea.modules.content.resume.education.Education, com.project.devidea.modules.content.resume.education.QEducation> educations = this.<com.project.devidea.modules.content.resume.education.Education, com.project.devidea.modules.content.resume.education.QEducation>createList("educations", com.project.devidea.modules.content.resume.education.Education.class, com.project.devidea.modules.content.resume.education.QEducation.class, PathInits.DIRECT2);

    public final StringPath github = createString("github");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<com.project.devidea.modules.content.resume.project.Project, com.project.devidea.modules.content.resume.project.QProject> projects = this.<com.project.devidea.modules.content.resume.project.Project, com.project.devidea.modules.content.resume.project.QProject>createList("projects", com.project.devidea.modules.content.resume.project.Project.class, com.project.devidea.modules.content.resume.project.QProject.class, PathInits.DIRECT2);

    public QResume(String variable) {
        this(Resume.class, forVariable(variable), INITS);
    }

    public QResume(Path<? extends Resume> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QResume(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QResume(PathMetadata metadata, PathInits inits) {
        this(Resume.class, metadata, inits);
    }

    public QResume(Class<? extends Resume> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.project.devidea.modules.account.QAccount(forProperty("account")) : null;
    }

}

