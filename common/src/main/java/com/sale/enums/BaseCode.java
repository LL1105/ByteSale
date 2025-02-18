package com.sale.enums;

/**
 * 接口返回码
 */
public enum BaseCode {
    /**
     * 基础code码
     * */
    SUCCESS(0, "OK"),
    
    SYSTEM_ERROR(-1,"系统异常，请稍后重试"),

    PAY_BILL_IS_NOT_NO_PAY(10011,"此账单不是未支付状态"),

    PAY_BILL_NOT_EXIST(10016,"账单不存在"),

    PAY_BILL_IS_NOT_PAY_STATUS(10023,"账单不是已支付状态"),

    REFUND_AMOUNT_GREATER_THAN_PAY_AMOUNT(10024,"退款金额大于支付金额"),

    // 用户相关
    USER_INFO_USERNAME_OR_PASSWORD_IS_NULL(20001, "用户名或密码为空"),

    USER_INFO_INVALID_USERNAME(20002, "无效的用户名"),

    USER_INFO_INVALID_PASSWORD(20003, "无效的密码"),

    USER_INFO_USERNAME_IS_EXIST(20004, "用户名已经存在"),

    USER_INFO_USERNAME_IS_NOT_EXIST(20005, "用户名不存在"),

    USER_INFO_PASSWORD_NOT_MATCH(20006, "密码不正确"),

    USER_INFO_INVALID_TOKEN(20007, "无效的token"),

    USER_INFO_LOGIN_OVER_ATTEMPTS(20008, "登录超过重试次数，等待5分钟"),

    USER_INFO_AVATAR_CHANGE_FAILED(20009, "头像更换失败"),

    USER_INFO_NOT_LOGIN(20010, "用户未登录"),

    FILE_IS_EMPTY(30001, "文件为空"),

    FILE_SIZE_EXCEED(30002, "超出文件上传大小"),

    FILE_TYPE_ILLEGAL(30003, "文件类型不合法"),
    ;
    
    private final Integer code;
    
    private String msg = "";
    
    BaseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public Integer getCode() {
        return this.code;
    }
    
    public String getMsg() {
        return this.msg == null ? "" : this.msg;
    }
    
    public static String getMsg(Integer code) {
        for (BaseCode re : BaseCode.values()) {
            if (re.code.intValue() == code.intValue()) {
                return re.msg;
            }
        }
        return "";
    }
    
    public static BaseCode getRc(Integer code) {
        for (BaseCode re : BaseCode.values()) {
            if (re.code.intValue() == code.intValue()) {
                return re;
            }
        }
        return null;
    }
}
