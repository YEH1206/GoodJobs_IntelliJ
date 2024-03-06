package com.portfolio.goodjobs.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCorporateInfo is a Querydsl query type for CorporateInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCorporateInfo extends EntityPathBase<CorporateInfo> {

    private static final long serialVersionUID = 1039725009L;

    public static final QCorporateInfo corporateInfo = new QCorporateInfo("corporateInfo");

    public final QMember _super = new QMember(this);

    public final StringPath ceo = createString("ceo");

    public final StringPath companyName = createString("companyName");

    //inherited
    public final BooleanPath del = _super.del;

    //inherited
    public final StringPath email = _super.email;

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final StringPath pw = _super.pw;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final NumberPath<Long> regNum = createNumber("regNum", Long.class);

    //inherited
    public final EnumPath<com.portfolio.goodjobs.enums.MemberRole> role = _super.role;

    public QCorporateInfo(String variable) {
        super(CorporateInfo.class, forVariable(variable));
    }

    public QCorporateInfo(Path<? extends CorporateInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCorporateInfo(PathMetadata metadata) {
        super(CorporateInfo.class, metadata);
    }

}

