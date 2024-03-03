package com.portfolio.goodjobs.enums.sigungu;

import com.portfolio.goodjobs.enums.CodeEnum;

public enum Gyeonggi implements CodeEnum {

    GAPYEONG("가평군", 41820),
    GOYANG("고양시", 41280),
    DEOKYANG("고양시 덕양군", 41281),
    ILSANDONGU("고양시 일산동구", 41285),
    ILSANSEOGU("고양시 일산서구", 41287),
    GWACHEON("과천시", 41290),
    GWANGMYEONG("광명시", 41210),
    GWANGJU("광주시", 41610),
    GURI("구리시", 41310),
    GUNPO("군포시", 41410),
    GIMPO("김포시", 41570),
    NAMYANGJU("남양주시", 41360),
    DONGDUCHEON("동두천시", 41250),
    SOSA("부천시 소사구", 41194),
    OJEONGGU("부천시 오정구", 41196),
    WONMIGU("부천시 원미구", 41192),
    BUNDANG("성남시 분당구", 41135),
    SUJEONG("성남시 수정구", 41131),
    JUNGWON("성남시 중원구", 41133),
    GWONSEON("수원시 권선구", 41113),
    YEONGTONG("수원시 영통구", 41117),
    JANGAN("수원시 장안구", 41111),
    PALDAL("수원시 팔달구", 41115);

    private final String name;
    private final int code;

    Gyeonggi(String name, int code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCode() {
        return code;
    }

}
