package com.nju.edu.erp.enums.sheetState;

import com.nju.edu.erp.enums.BaseEnum;

public enum PayRollState implements BaseEnum<PayRollState, String> {
    DRAFT("草稿"), // 待财务人员确认
    PENDING("待审批"), // 待总经理审批
    SUCCESS("审批完成"),
    FAILURE("审批失败");

    private final String value;

    PayRollState(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
