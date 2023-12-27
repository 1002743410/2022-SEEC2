package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayRollPO {
    /**
     * 单据编号 格式为（KJD-yyyyMMdd-xxxxx）
     */
    private String id;
    /**
     * 员工id
     */
    private Integer staff;
    /**
     * 员工姓名
     */
    private String name;
    /**
     * 银行账户id
     */
    private Integer bid;
    /**
     * 应发工资
     */
    private BigDecimal gross_pay;
    /**
     * 扣除税款
     */
    private BigDecimal tax;
    /**
     * 实发工资
     */
    private BigDecimal real_pay;

}
