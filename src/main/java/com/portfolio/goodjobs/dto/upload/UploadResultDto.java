package com.portfolio.goodjobs.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDto {

    private String uuid;

    private String fileName;

    /**
     * 나중에 JSON으로 처리될 때 link 속성으로 자동 처리된다.
     */
    public String getLink() {
        return uuid + "_" + fileName;
    }
}
