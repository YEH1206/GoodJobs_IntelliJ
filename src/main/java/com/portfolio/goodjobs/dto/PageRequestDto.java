package com.portfolio.goodjobs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDto {

    @Builder.Default
    private int page = 1;               // 조회할 페이지 번호

    @Builder.Default
    private int size = 20;              // 페이지 당 채용공고 개수

    private String location;           // 검색할 지역 리스트 (구분자:+)

    private String keyword;             // 검색 키워드

    @Builder.Default
    private boolean closed = false;       // 마감된 공고 검색 여부 (T:포함, F:제외)

    @Builder.Default
    private String sortType = "no";      // 정렬 기준: no(최신등록순), deadline(마감임박순)

    private String link;                // 현재 페이징 상태를 문자열로 구성

    public String[] getLocations() {
        if(location == null || location.isEmpty()) return null;
        return location.split("\\+");
    }

    public Pageable getPageable() {
        Sort sort = Sort.by(sortType);

        if("no".equals(sortType)) {
            // 최신등록순 정렬
            sort.descending();
        } else if("deadline".equals(sortType)) {
            // 마감임박순 정렬
            sort.ascending();
        }

        return PageRequest.of(this.page - 1, this.size, sort);
    }

    public String getLink() {

        if(link == null) {
            StringBuilder builder = new StringBuilder();
            builder.append("page=").append(this.page);
            builder.append("&size=").append(this.size);

            if(closed) {
                builder.append("&closed=").append("true");
            } else {
                builder.append("&closed=").append("false");
            }

            if(location != null && !location.isEmpty()) builder.append("&location=").append(location);

            if(keyword != null && !keyword.isEmpty()) {
                builder.append("&keyword=").append(URLEncoder.encode(keyword, StandardCharsets.UTF_8));
            }

            if(sortType != null && !sortType.isEmpty()) builder.append("&sortType=").append(sortType);

            link = builder.toString();
        }
        return link;
    }
}
