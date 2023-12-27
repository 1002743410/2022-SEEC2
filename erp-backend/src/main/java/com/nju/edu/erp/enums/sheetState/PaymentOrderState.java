package com.nju.edu.erp.enums.sheetState;

import com.nju.edu.erp.enums.BaseEnum;

public enum PaymentOrderState implements BaseEnum<PaymentOrderState, String> {
    DRAFT("草稿"), // 待财务人员确认
    PENDING("待审批"), // 待总经理审批
    SUCCESS("审批完成"),
    FAILURE("审批失败"),
    DONE("处理完成");

    private final String value;

    PaymentOrderState(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

}
