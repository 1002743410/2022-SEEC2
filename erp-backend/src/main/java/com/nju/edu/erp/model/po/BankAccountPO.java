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
public class BankAccountPO {
    /**
     * 银行账户id
     */
    private Integer id;
    /**
     * 银行账户金额
     */
    private BigDecimal bankAmount;
}
