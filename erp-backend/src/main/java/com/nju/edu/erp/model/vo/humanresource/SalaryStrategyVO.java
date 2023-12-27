package com.nju.edu.erp.model.vo.humanresource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryStrategyVO {
    private String role;
    private Integer basicsalary;
    private Integer postsalary;
    private Integer commission;
    private Double punishmentrate;
}
