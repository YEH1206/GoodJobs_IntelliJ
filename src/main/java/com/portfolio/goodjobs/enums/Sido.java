package com.portfolio.goodjobs.enums;

public enum Sido {
    SEOUL("서울", 11),
    GYEONGGI("경기", 41);

    private final String name;
    private final int code;

    Sido(String name, int code) {
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
