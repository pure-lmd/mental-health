package com.ti.mental.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.ti.mental.common.exception.BusinessException;
import com.ti.mental.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 文件上传工具类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Slf4j
@Component
public class FileUtil {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.upload.max-size}")
    private Long maxSize;

    @Value("${file.upload.allowed-types}")
    private String allowedTypes;

    private List<String> allowedTypeList;

    @PostConstruct
    public void init() {
        // 解析允许的文件类型
        allowedTypeList = Arrays.asList(allowedTypes.split(","));
        // 将相对路径转为绝对路径
        File uploadDir = new File(uploadPath);
        if (!uploadDir.isAbsolute()) {
            // 获取项目根目录（即 user.dir）
            String projectDir = System.getProperty("user.dir");
            uploadPath = projectDir + File.separator + uploadPath;
            uploadDir = new File(uploadPath);
        }
        // 确保路径以分隔符结尾
        if (!uploadPath.endsWith("/") && !uploadPath.endsWith("\\")) {
            uploadPath = uploadPath + File.separator;
        }
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            if (created) {
                log.info("创建上传目录: {}", uploadDir.getAbsolutePath());
            }
        }
        log.info("文件上传目录: {}", uploadDir.getAbsolutePath());
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件访问路径
     */
    public String upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ResultCode.FILE_UPLOAD_ERROR);
        }

        // 检查文件大小
        if (file.getSize() > maxSize) {
            throw new BusinessException(ResultCode.FILE_SIZE_ERROR);
        }

        // 检查文件类型
        String contentType = file.getContentType();
        if (StrUtil.isBlank(contentType) || !allowedTypeList.contains(contentType)) {
            throw new BusinessException(ResultCode.FILE_TYPE_ERROR);
        }

        // 获取原始文件名和扩展名
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (StrUtil.isNotBlank(originalFilename) && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // 生成新文件名：日期 + UUID + 扩展名
        String dateDir = DateUtil.format(new Date(), "yyyy/MM/dd");
        String newFileName = IdUtil.fastSimpleUUID() + extension;

        // 创建日期目录
        File dateFolder = new File(uploadPath + dateDir);
        if (!dateFolder.exists()) {
            boolean created = dateFolder.mkdirs();
            if (created) {
                log.info("创建日期目录: {}", dateFolder.getAbsolutePath());
            }
        }

        // 保存文件
        File destFile = new File(dateFolder, newFileName);
        try {
            file.transferTo(destFile);
            log.info("文件上传成功: {}", destFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("文件上传失败: {}", e.getMessage());
            throw new BusinessException(ResultCode.FILE_UPLOAD_ERROR);
        }

        // 返回访问路径
        return "/uploads/" + dateDir + "/" + newFileName;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径（相对路径）
     */
    public void delete(String filePath) {
        if (StrUtil.isBlank(filePath)) {
            return;
        }
        // 去除 /uploads/ 前缀
        String relativePath = filePath.replace("/uploads/", "");
        File file = new File(uploadPath + relativePath);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                log.info("文件删除成功: {}", file.getAbsolutePath());
            }
        }
    }
}
