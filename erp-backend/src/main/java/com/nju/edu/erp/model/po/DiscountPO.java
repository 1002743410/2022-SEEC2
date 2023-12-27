package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountPO {
    private Integer id;
    private Integer type;
    private Integer customerLevel;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private BigDecimal voucherAmount;
    private Date createTime;
}
