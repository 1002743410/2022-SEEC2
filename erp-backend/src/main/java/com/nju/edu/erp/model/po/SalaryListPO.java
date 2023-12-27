package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryListPO {
    private String name;
    private String role;
    private Integer basicsalary;
    private Integer postsalary;
    private Integer commission;
    private Integer bonus;
    private Integer uncheck;
    private Double tax;
    private Double realsalary;
    private String year;
    private String month;
    private String state;
}

