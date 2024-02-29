package com.portfolio.goodjobs.dto.upload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UploadFileDto {

    private List<MultipartFile> files;
    private boolean thumbnail;              // 썸네일 저장 여부

}
