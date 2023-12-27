package com.nju.edu.erp.model.vo.humanresource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateRangeCheckVO {
    String name;
    String role;
    Integer times;
}
