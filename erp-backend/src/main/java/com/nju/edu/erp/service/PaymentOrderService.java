package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.PaymentOrderState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.finance.PaymentOrderVO;
import com.nju.edu.erp.model.vo.finance.ReceiptVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentOrderService {
    /**
     * 制定付款单
     * @param userVO
     * @param paymentOrderVO
     */
    void makePaymentOrder(PaymentOrderVO paymentOrderVO);

    /**
     * 根据状态获取付款单
     * @param state
     * @return   付款单列表
     */
    List<PaymentOrderVO> findPaymentOrderByState(PaymentOrderState state);

    /**
     * 单据审批
     * @param paymentOrderId
     * @param state 审核完之后的状态
     */
    void approval(String paymentOrderId, PaymentOrderState state);

    /**
     * 根据id获取付款单信息
     * @param paymentOrderId
     * @return   付款单
     */
    PaymentOrderVO findPaymentOrderById(String paymentOrderId);

    String makeChange(String paymentOrderId);
}

