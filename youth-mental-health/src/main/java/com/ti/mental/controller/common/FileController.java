package com.ti.mental.controller.common;

import com.ti.mental.common.result.Result;
import com.ti.mental.common.utils.FileUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传Controller
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "文件上传")
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Resource
    private FileUtil fileUtil;

    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        String url = fileUtil.upload(file);
        return Result.success(url);
    }
}
