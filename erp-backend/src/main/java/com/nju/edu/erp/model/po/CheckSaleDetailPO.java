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
public class CheckSaleDetailPO {
    private String t;
    private Date createTime;
    private String name;
    private String type;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
