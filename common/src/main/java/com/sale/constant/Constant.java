package com.sale.constant;

/**
 * 常量
 */
public class Constant {
    
    /**
     * 链路id
     * */
    public static final String TRACE_ID = "traceId";
    
    public static final String GRAY_FLAG_TRUE = "true";
    
    public static final String GRAY_FLAG_FALSE = "false";
    
    public static final String GRAY_PARAMETER = "gray";
    
    public static final String CODE = "code";
    
    public static final String USER_ID = "userId";
    
    public static final String JOB_INFO_ID = "jobInfoId";
    
    public static final String JOB_RUN_RECORD_ID = "jobRunRecordId";
    
    public static final String ALIPAY_NOTIFY_SUCCESS_RESULT = "success";
    
    public static final String ALIPAY_NOTIFY_FAILURE_RESULT = "failure";
    
    public static final String PREFIX_DISTINCTION_NAME = "prefix.distinction.name";
    
    public static final String DEFAULT_PREFIX_DISTINCTION_NAME = "damai";
    
    public static final String SPRING_INJECT_PREFIX_DISTINCTION_NAME = "${"+PREFIX_DISTINCTION_NAME+":"+DEFAULT_PREFIX_DISTINCTION_NAME+"}";
    
    public static final String SERVER_GRAY = "${spring.cloud.nacos.discovery.metadata.gray:false}";
    
}
