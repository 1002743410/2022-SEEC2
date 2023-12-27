package com.nju.edu.erp.model.vo.humanresource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostInformanceVO {
    private String postname;
    private Integer basicsalary;
    private Integer postsalary;
    private String role;
    private String salarycalculation;
    private String salarygrant;
    private String tax;
}
