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

    private String type;                // 검색 조건: l(지역별), p(채용중) 조합

    private String keyword;             // 검색 키워드

    private String link;                // 현재 페이징 상태를 문자열로 구성

    public String[] getTypes() {
        if(type == null || type.isEmpty()) return null;
        return type.split("");
    }

    public Pageable getPageable(String... props) {
        return PageRequest.of(this.page - 1, this.size, Sort.by(props).descending());
    }

    public String getLink() {

        if(link == null) {
            StringBuilder builder = new StringBuilder();
            builder.append("page=").append(this.page);
            builder.append("&size=").append(this.size);

            if(type != null && !type.isEmpty()) builder.append("&type=").append(type);

            if(keyword != null && !keyword.isEmpty()) {
                builder.append("&keyword=").append(URLEncoder.encode(keyword, StandardCharsets.UTF_8));
            }
            link = builder.toString();
        }
        return link;
    }
}
