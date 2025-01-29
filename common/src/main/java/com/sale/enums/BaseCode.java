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
