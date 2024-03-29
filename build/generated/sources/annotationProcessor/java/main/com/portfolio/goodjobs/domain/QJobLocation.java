package com.portfolio.goodjobs.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJobLocation is a Querydsl query type for JobLocation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJobLocation extends EntityPathBase<JobLocation> {

    private static final long serialVersionUID = 1010142512L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJobLocation jobLocation = new QJobLocation("jobLocation");

    public final QJob job;

    public final NumberPath<Long> no = createNumber("no", Long.class);

    public final NumberPath<Integer> ord = createNumber("ord", Integer.class);

    public final NumberPath<Integer> sigungu = createNumber("sigungu", Integer.class);

    public QJobLocation(String variable) {
        this(JobLocation.class, forVariable(variable), INITS);
    }

    public QJobLocation(Path<? extends JobLocation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJobLocation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJobLocation(PathMetadata metadata, PathInits inits) {
        this(JobLocation.class, metadata, inits);
    }

    public QJobLocation(Class<? extends JobLocation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.job = inits.isInitialized("job") ? new QJob(forProperty("job")) : null;
    }

}

