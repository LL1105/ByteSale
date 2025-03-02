package com.sale.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oss.qiniu")
@Data
public class QiniuConfig {
    /**
     * AC
     */
    private String accessKey;
    /**
     * SC
     */
    private String secretKey;
    /**
     * 存储空间
     */
    private String bucket;
    /**
     * 上传目录
     */
    private String directory;
    /**
     * 访问域名
     */
    private String domain;
}
