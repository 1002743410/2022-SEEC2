package com.nju.edu.erp.model.po;

import com.nju.edu.erp.enums.sheetState.PaymentOrderState;
import com.nju.edu.erp.enums.sheetState.ReceiptState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentOrderPO {
    /**
     * 单据编号 （格式为：XJFYD-yyyyMMdd-xxxxx）
     */
    private String id;
    /**
     * 客户id（供应商/销售商）
     */
    private Integer custom;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 银行账户id
     */
    private Integer bid;
    /**
     * 转账总额
     */
    private BigDecimal totalAmount;
    /**
     * 单据状态
     */
    private PaymentOrderState state;
}