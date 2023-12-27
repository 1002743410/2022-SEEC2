package com.nju.edu.erp.model.po;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckPO {
//    签到
    /**
     * 员工姓名
     */
    private String name;
    /**
     * 员工种类
     */
    private String role;
    /**
     * 签到时间
     */
    private String checktime;
}
