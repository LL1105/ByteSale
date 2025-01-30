package com.sale.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 退款表
 * @TableName refund_bill
 */
@TableName(value ="refund_bill")
@Data
public class RefundBill implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 商户订单号
     */
    private String outOrderNo;

    /**
     * 账单id
     */
    private Long payBillId;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 账单退款状态 1：未退款 2：已退款
     */
    private Integer refundStatus;

    /**
     * 退款时间
     */
    private Date refundTime;

    /**
     * 退款原因
     */
    private String reason;

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
        RefundBill other = (RefundBill) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOutOrderNo() == null ? other.getOutOrderNo() == null : this.getOutOrderNo().equals(other.getOutOrderNo()))
            && (this.getPayBillId() == null ? other.getPayBillId() == null : this.getPayBillId().equals(other.getPayBillId()))
            && (this.getRefundAmount() == null ? other.getRefundAmount() == null : this.getRefundAmount().equals(other.getRefundAmount()))
            && (this.getRefundStatus() == null ? other.getRefundStatus() == null : this.getRefundStatus().equals(other.getRefundStatus()))
            && (this.getRefundTime() == null ? other.getRefundTime() == null : this.getRefundTime().equals(other.getRefundTime()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getEditTime() == null ? other.getEditTime() == null : this.getEditTime().equals(other.getEditTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOutOrderNo() == null) ? 0 : getOutOrderNo().hashCode());
        result = prime * result + ((getPayBillId() == null) ? 0 : getPayBillId().hashCode());
        result = prime * result + ((getRefundAmount() == null) ? 0 : getRefundAmount().hashCode());
        result = prime * result + ((getRefundStatus() == null) ? 0 : getRefundStatus().hashCode());
        result = prime * result + ((getRefundTime() == null) ? 0 : getRefundTime().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
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
        sb.append(", outOrderNo=").append(outOrderNo);
        sb.append(", payBillId=").append(payBillId);
        sb.append(", refundAmount=").append(refundAmount);
        sb.append(", refundStatus=").append(refundStatus);
        sb.append(", refundTime=").append(refundTime);
        sb.append(", reason=").append(reason);
        sb.append(", createTime=").append(createTime);
        sb.append(", editTime=").append(editTime);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}