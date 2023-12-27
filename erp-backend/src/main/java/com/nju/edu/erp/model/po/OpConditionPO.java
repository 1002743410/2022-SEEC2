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
public class OpConditionPO {
    BigDecimal discountAmount;
    BigDecimal totalIncome;
    BigDecimal totalOutcome;
    BigDecimal profit;
}
