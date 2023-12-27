package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.PaymentOrderState;
import com.nju.edu.erp.model.po.PaymentOrderPO;
import com.nju.edu.erp.model.po.TermSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PaymentOrderDao {
    /**
     *获取最近一条付款单
     */
    PaymentOrderPO getLatestPaymentOrder();

    /**
     * 存入一条付款单记录
     * @param toSave
     * @return  影响的行数
     */
    int savePaymentOrder(PaymentOrderPO toSave);

    /**
     * 把付款单上的条目清单存入数据库
     * @param termSheets
     */
    void saveTermBatch(TermSheetPO termSheets);

    /**
     * 返回所有付款单
     * @return 付款单列表
     */
    List<PaymentOrderPO> findAllPaymentOrder();

    /**
     * 根据状态返回付款单
     */
    List<PaymentOrderPO> findPaymentOrderByState(PaymentOrderState state);

    int updateState(String paymentOrderId, PaymentOrderState state);

    PaymentOrderPO findOnePaymentOrderById(String paymentOrderId);

    List<TermSheetPO> findTermSheetById(String paymentOrderId);
}
