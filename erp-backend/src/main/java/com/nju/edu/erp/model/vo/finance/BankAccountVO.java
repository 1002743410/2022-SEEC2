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
public class BankAccountVO {
    /**
     * 银行账户id
     */
    private Integer id;
    /**
     * 银行账户金额
     */
    private BigDecimal bankAmount;
}
