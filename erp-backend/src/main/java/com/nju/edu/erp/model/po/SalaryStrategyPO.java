package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryStrategyPO {
    private String role;
    private Integer basicsalary;
    private Integer postsalary;
    private Integer commission;
    private Double punishmentrate;
}
