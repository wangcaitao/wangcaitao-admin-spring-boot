package com.wangcaitao.admin.upload.controller;

import com.wangcaitao.common.entity.Result;
import com.wangcaitao.starter.service.UploadService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangcaitao
 */
@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> upload(MultipartFile file, String prefix) {
        return uploadService.upload(file, prefix);
    }

    @PostMapping(value = "/upload-batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<List<String>> upload(MultipartHttpServletRequest request, String prefix) {
        return uploadService.upload(request, prefix);
    }
}
