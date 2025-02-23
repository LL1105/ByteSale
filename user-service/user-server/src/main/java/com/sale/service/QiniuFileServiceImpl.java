package com.sale.service;


import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.sale.config.QiniuConfig;
import com.sale.enums.BaseCode;
import com.sale.utils.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Service
public class QiniuFileServiceImpl implements IFileService{
    @Autowired
    private QiniuConfig qiniuConfig;

    @Override
    public String upload(MultipartFile file) throws IOException {
        if(file.isEmpty()) {
            throw new RuntimeException(BaseCode.FILE_IS_EMPTY.getMsg());
        }
        // 1、获取上传Token
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        String upToken = auth.uploadToken(qiniuConfig.getBucket());

        // 2、设置上传配置
        Configuration cfg = new Configuration(Region.huanan());

        // 3. 创建上传管理器
        UploadManager uploadManager = new UploadManager(cfg);

        // 上传文件
        String fileKey = genFileName(file);
        Response response = uploadManager.put(file.getInputStream(), fileKey, upToken, null, null);

        // 返回文件url
        return String.format("%s/%s", qiniuConfig.getDomain(), fileKey);
    }

    @Override
    public InputStream download(String url) throws Exception {
        return null;
    }

    public String genFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        String suffix = null;
        if(!Objects.isNull(originalFilename)) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        return qiniuConfig.getDirectory() + UUID.randomUUID() + suffix;
    }
}
