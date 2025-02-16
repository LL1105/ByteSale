package com.sale.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件操作服务
 */
public interface IFileService {
    /**
     * 文件上传
     *
     * @param file 待上传的文件
     * @return 访问该文件的url
     */
    public String upload(MultipartFile file) throws IOException;

    // 文件下载
    public InputStream download(String url) throws Exception;
}
