package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.ReceiptState;
import com.nju.edu.erp.model.po.ReceiptPO;
import com.nju.edu.erp.model.po.TransferAccountPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ReceiptDao {
    /**
     *获取最近一条收款单
     */
    ReceiptPO getLatestReceipt();

    /**
     * 存入一条收款单记录
     * @param toSave
     * @return  影响的行数
     */
    int saveReceipt(ReceiptPO toSave);

    /**
     * 把收款单上的转账记录存入数据库
     * @param transferAccount
     */
    void saveTransferBatch(TransferAccountPO transferAccount);

    /**
     * 返回所有收款单
     * @return 收款单列表
     */
    List<ReceiptPO> findAllReceipt();

    /**
     * 根据状态返回收款单
     */
    List<ReceiptPO> findReceiptByState(ReceiptState state);

    int updateState(String receiptId, ReceiptState state);

    ReceiptPO findOneReceiptById(String receiptId);

    List<TransferAccountPO> findTransferAccountById(String receiptId);
}
