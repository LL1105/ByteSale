package com.sale.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 支付表
 * @TableName pay_bill
 */
@TableName(value ="pay_bill")
@Data
public class PayBill implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 支付流水号
     */
    private String payNumber;

    /**
     * 商户订单号
     */
    private String outOrderNo;

    /**
     * 支付渠道
     */
    private String payChannel;

    /**
     * 支付环境
     */
    private String payScene;

    /**
     * 订单标题
     */
    private String subject;

    /**
     * 三方交易凭证号
     */
    private String tradeNumber;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 支付种类 详细见枚举PayBillType
     */
    private Integer payBillType;

    /**
     * 账单支付状态 详细见枚举PayBillStatus
     */
    private Integer payBillStatus;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date editTime;

    /**
     * 状态 1：未删除 0：删除
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PayBill other = (PayBill) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPayNumber() == null ? other.getPayNumber() == null : this.getPayNumber().equals(other.getPayNumber()))
            && (this.getOutOrderNo() == null ? other.getOutOrderNo() == null : this.getOutOrderNo().equals(other.getOutOrderNo()))
            && (this.getPayChannel() == null ? other.getPayChannel() == null : this.getPayChannel().equals(other.getPayChannel()))
            && (this.getPayScene() == null ? other.getPayScene() == null : this.getPayScene().equals(other.getPayScene()))
            && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()))
            && (this.getTradeNumber() == null ? other.getTradeNumber() == null : this.getTradeNumber().equals(other.getTradeNumber()))
            && (this.getPayAmount() == null ? other.getPayAmount() == null : this.getPayAmount().equals(other.getPayAmount()))
            && (this.getPayBillType() == null ? other.getPayBillType() == null : this.getPayBillType().equals(other.getPayBillType()))
            && (this.getPayBillStatus() == null ? other.getPayBillStatus() == null : this.getPayBillStatus().equals(other.getPayBillStatus()))
            && (this.getPayTime() == null ? other.getPayTime() == null : this.getPayTime().equals(other.getPayTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getEditTime() == null ? other.getEditTime() == null : this.getEditTime().equals(other.getEditTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPayNumber() == null) ? 0 : getPayNumber().hashCode());
        result = prime * result + ((getOutOrderNo() == null) ? 0 : getOutOrderNo().hashCode());
        result = prime * result + ((getPayChannel() == null) ? 0 : getPayChannel().hashCode());
        result = prime * result + ((getPayScene() == null) ? 0 : getPayScene().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        result = prime * result + ((getTradeNumber() == null) ? 0 : getTradeNumber().hashCode());
        result = prime * result + ((getPayAmount() == null) ? 0 : getPayAmount().hashCode());
        result = prime * result + ((getPayBillType() == null) ? 0 : getPayBillType().hashCode());
        result = prime * result + ((getPayBillStatus() == null) ? 0 : getPayBillStatus().hashCode());
        result = prime * result + ((getPayTime() == null) ? 0 : getPayTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getEditTime() == null) ? 0 : getEditTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", payNumber=").append(payNumber);
        sb.append(", outOrderNo=").append(outOrderNo);
        sb.append(", payChannel=").append(payChannel);
        sb.append(", payScene=").append(payScene);
        sb.append(", subject=").append(subject);
        sb.append(", tradeNumber=").append(tradeNumber);
        sb.append(", payAmount=").append(payAmount);
        sb.append(", payBillType=").append(payBillType);
        sb.append(", payBillStatus=").append(payBillStatus);
        sb.append(", payTime=").append(payTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", editTime=").append(editTime);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}