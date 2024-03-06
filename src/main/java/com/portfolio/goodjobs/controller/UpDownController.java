package com.portfolio.goodjobs.controller;

import com.portfolio.goodjobs.dto.upload.UploadFileDto;
import com.portfolio.goodjobs.dto.upload.UploadResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.*;

@RestController
@Log4j2
public class UpDownController {

    @Value("${com.portfolio.goodjobs.temp.path}")
    private String tempPath;

    @Operation(summary = "전송된 파일을 저장한다.")
    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<UploadResultDto>> upload(@RequestBody UploadFileDto uploadFileDto) {

        if(uploadFileDto.getFiles() != null) {
            final List<UploadResultDto> list = new ArrayList<>();

            for (MultipartFile multipartFile : uploadFileDto.getFiles()) {
                String originalName = multipartFile.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();

                // 전송된 파일을 저장한다.
                Path savePath = Paths.get(tempPath, uuid + "_" + originalName);
                try {
                    Files.copy(multipartFile.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);

                    if(uploadFileDto.isThumbnail()) {
                        // 최대 폭이 200인 원본 비율의 썸네일을 생성한다.
                        int thumbnailSize = 200;
                        File thumbFile = new File(tempPath, "s_" + uuid + "_" + originalName);
                        Thumbnails.of(savePath.toFile())
                                .size(thumbnailSize, thumbnailSize)
                                .outputQuality(1.0)
                                .toFile(thumbFile);

                        BufferedImage originalThumbnail = ImageIO.read(thumbFile);
                        int originalWidth = originalThumbnail.getWidth();
                        int originalHeight = originalThumbnail.getHeight();

                        // 정사각형의 투명한 캔버스를 생성한다.
                        BufferedImage squareImage =
                                new BufferedImage(thumbnailSize, thumbnailSize, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = squareImage.createGraphics();
                        g2d.setColor(new Color(0, 0, 0, 0));
                        g2d.fillRect(0, 0, thumbnailSize, thumbnailSize);

                        // 로고 이미지가 정가운데에 위치하도록 설정한다.
                        int x = Math.max((thumbnailSize - originalWidth) / 2, 0);
                        int y = Math.max((thumbnailSize - originalHeight) / 2, 0);
                        g2d.drawImage(originalThumbnail, x, y, originalWidth, originalHeight, null);
                        g2d.dispose();

                        ImageIO.write(squareImage, "png", thumbFile);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }

                list.add(UploadResultDto.builder()
                        .uuid(uuid)
                        .fileName(originalName)
                        .build());
            }//end of loop
            return ResponseEntity.status(HttpStatus.CREATED).body(list);
        }// end of if
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(summary = "저장된 첨부파일을 조회한다.")
    @GetMapping("/files/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName) {

        Resource resource = new FileSystemResource(tempPath + File.separator + fileName);
        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @Operation(summary = "저장된 첨부파일을 삭제한다.")
    @DeleteMapping("/files/{fileName}")
    public Map<String, Boolean> removeFile(@PathVariable String fileName) {

        Resource resource = new FileSystemResource(tempPath + File.separator + fileName);

        Map<String, Boolean> resultMap = new HashMap<>();
        boolean removed = false;

        try {
            removed = resource.getFile().delete();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        resultMap.put("result", removed);

        return resultMap;
    }
}
