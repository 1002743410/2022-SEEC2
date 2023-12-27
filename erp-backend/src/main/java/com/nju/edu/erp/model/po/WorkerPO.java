package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkerPO {
    private String name;
    private String sex;
    private String birthday;
    private String role;
    private String inductionday;
    private String phone;
    private String email;
    private String salarycard;
}