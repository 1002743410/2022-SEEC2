package com.nju.edu.erp.model.vo.finance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TermSheetVO {
    /**
     * 条目名id
     */
    private Integer id;
    /**
     * 付款单id
     */
    private String paymentOrderId;

    /**
     * 转账金额
     */
    private BigDecimal amount;
    /**
     * 备注
     */
    private String remark;
}
