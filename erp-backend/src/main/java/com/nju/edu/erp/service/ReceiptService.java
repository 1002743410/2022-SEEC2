package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.ReceiptState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.finance.ReceiptVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReceiptService {

    /**
     * 制定收款单
     * @param userVO
     * @param receiptVO
     */
    void makeReceipt( ReceiptVO receiptVO);

    /**
     * 根据状态获取收款单
     * @param state
     * @return
     */
    List<ReceiptVO> getReceiptByState(ReceiptState state);

    /**
     * 审批单据
     * @param receiptId
     * @param state 进货单修改后的状态
     */
    void approval(String receiptId, ReceiptState state);

    /**
     * 根据id获取收款单信息
     * @param receiptId
     * @return   收款单
     */
    ReceiptVO getReceiptById(String receiptId);

    String makeChange(String receiptId);


}