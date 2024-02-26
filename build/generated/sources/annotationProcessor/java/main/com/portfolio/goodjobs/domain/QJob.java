package com.portfolio.goodjobs.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QJob is a Querydsl query type for Job
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJob extends EntityPathBase<Job> {

    private static final long serialVersionUID = -2046028709L;

    public static final QJob job = new QJob("job");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath address = createString("address");

    public final StringPath companyName = createString("companyName");

    public final DateTimePath<java.time.LocalDateTime> deadline = createDateTime("deadline", java.time.LocalDateTime.class);

    public final NumberPath<Byte> edu = createNumber("edu", Byte.class);

    public final BooleanPath exp = createBoolean("exp");

    public final NumberPath<Short> expYear = createNumber("expYear", Short.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final NumberPath<Long> no = createNumber("no", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath title = createString("title");

    public final StringPath writer = createString("writer");

    public QJob(String variable) {
        super(Job.class, forVariable(variable));
    }

    public QJob(Path<? extends Job> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJob(PathMetadata metadata) {
        super(Job.class, metadata);
    }

}

