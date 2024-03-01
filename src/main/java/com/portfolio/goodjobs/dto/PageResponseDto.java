package com.portfolio.goodjobs.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * 제네릭 타입으로 선언해 다양한 Entity를 활용할 수 있게 한다.
 */
@Getter
@ToString
public class PageResponseDto<E> {

    private int page;               // 조회할 페이지 번호
    private int size;               // 페이지 당 채용공고 수
    private int total;              // DB에 저장된 전체 채용공고 수
    private List<E> dtoList;        // 전체 채용공고 리스트

    // 네비게이션바 속성
    private int start;              // 시작 번호
    private int end;                // 끝 번호
    private boolean prev;           // 이전 버튼 여부
    private boolean next;           // 다음 버튼 여부

    @Builder(builderMethodName = "withAll")
    public PageResponseDto(PageRequestDto pageRequestDto, List<E> dtoList, int total) {

        if(total <= 0) return;

        this.page = pageRequestDto.getPage();
        this.size = pageRequestDto.getSize();
        this.total = total;
        this.dtoList = dtoList;

        this.end = (int)(Math.ceil(this.page/10.0)) * 10;       // 10페이지 단위로 네비게이션바 생성
        this.start = this.end - 9;
        int last = (int)(Math.ceil(total/(double)size));        // 전체 페이지 수
        this.end = Math.min(this.end, last);

        this.prev = this.start > 1;
        this.next = last > this.end;
    }
}
