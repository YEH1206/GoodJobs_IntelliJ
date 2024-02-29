package com.portfolio.goodjobs.enums.sigungu;

public enum Seoul {

    GANGNAM("강남구", 11680),
    GANGDONG("강동구", 11740),
    GANGBUK("강북구", 11305),
    GANGSEO("강서구", 11500),
    GWANAK("관악구", 11620),
    GWANGJIN("광진구", 11215),
    GURO("구로구", 11530),
    GEUMCHEON("금천구", 11545),
    NOWON("노원구", 11350),
    DOBONG("도봉구", 11320),
    DONGDAEMUN("동대문구", 11230),
    DONGJAK("동작구", 11590),
    MAPO("마포구", 11440),
    SEODAEMUN("서대문구", 11410),
    SEOCHO("서초구", 11650),
    SEONGDONG("성동구", 11200),
    SEONGBUK("성북구", 11290),
    SONGPA("송파구", 11710),
    YANGCHEON("양천구", 11470),
    YEONGDEUNGPO("영등포구", 11560),
    YONGSAN("용산구", 11170),
    EUNPYEONG("은평구", 11380),
    JONGRO("종로구", 11110),
    JUNG("중구", 11140),
    JUNGRANG("중랑구", 11260);

    private final String name;
    private final int code;

    Seoul(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

}