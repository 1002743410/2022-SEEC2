package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.BankAccountDao;
import com.nju.edu.erp.dao.CustomerDao;
import com.nju.edu.erp.dao.ReceiptDao;
import com.nju.edu.erp.enums.sheetState.PaymentOrderState;
import com.nju.edu.erp.enums.sheetState.ReceiptState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.finance.ReceiptVO;
import com.nju.edu.erp.model.vo.finance.TransferAccountVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.ReceiptService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiptServiceImpl implements ReceiptService {
    ReceiptDao receiptDao;
    CustomerService customerService;
    CustomerDao customerDao;
    BankAccountDao bankAccountDao;

    @Autowired
    public ReceiptServiceImpl(ReceiptDao receiptDao, CustomerService customerService, CustomerDao customerDao,BankAccountDao bankAccountDao){
        this.receiptDao = receiptDao;
        this.customerService = customerService;
        this.customerDao = customerDao;
        this.bankAccountDao = bankAccountDao;
    }

    /**
     * 制定收款单
     * @param
     * @param receiptVO
     */
    @Override
    @Transactional
    public void makeReceipt(ReceiptVO receiptVO){
        ReceiptPO receiptPO = new ReceiptPO();
        BeanUtils.copyProperties(receiptVO,receiptPO);
        // 此处根据制定单据人员确定操作员

        ReceiptPO latest = receiptDao.getLatestReceipt();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "SKD");
        receiptPO.setId(id);
        receiptPO.setState(ReceiptState.PENDING);

        BigDecimal totalAmount = BigDecimal.ZERO;

        List<TransferAccountPO> tAccountPOList = new ArrayList<>();
        for(TransferAccountVO content:receiptVO.getTransferAccountVOS()){
            TransferAccountPO tAccountPO = new TransferAccountPO();
            BeanUtils.copyProperties(content,tAccountPO);
            tAccountPO.setReceiptId(id);
            BigDecimal amount = tAccountPO.getAmount();
            tAccountPOList.add(tAccountPO);
            totalAmount = totalAmount.add(amount);
        }
        for(TransferAccountPO i:tAccountPOList){
            receiptDao.saveTransferBatch(i);
        }
        //receiptDao.saveTransferBatch(tAccountPOList);
        receiptPO.setTotalAmount(totalAmount);
        receiptDao.saveReceipt(receiptPO);

    }

    /**
     * 根据状态获取收款单
     * @param state
     * @return
     */
    @Override
    public List<ReceiptVO> getReceiptByState(ReceiptState state){
        List<ReceiptVO> res = new ArrayList<>();
        List<ReceiptPO> all;
        if(state == null){
            all = receiptDao.findAllReceipt();
        }else{
            all = receiptDao.findReceiptByState(state);
        }
        for(ReceiptPO po : all){
            ReceiptVO vo = new ReceiptVO();
            BeanUtils.copyProperties(po, vo);
            List<TransferAccountPO> alll = receiptDao.findTransferAccountById(po.getId());
            List<TransferAccountVO> vos = new ArrayList<>();
            for(TransferAccountPO p : alll){
                TransferAccountVO v = new TransferAccountVO();
                BeanUtils.copyProperties(p,v);
                vos.add(v);
            }
            vo.setTransferAccountVOS(vos);
            res.add(vo);
        }
        return res;
    }

    /**
     * 审批单据
     * @param receiptId
     * @param state 进货单修改后的状态(审核失败/审核成功)
     */
    @Override
    @Transactional
    public void approval(String receiptId, ReceiptState state){
        if(state.equals(ReceiptState.FAILURE)) {
            ReceiptPO receipt = receiptDao.findOneReceiptById(receiptId);
            if(receipt.getState() == ReceiptState.SUCCESS) throw new RuntimeException("状态更新失败");
            int effectLines = receiptDao.updateState(receiptId,state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
        }
        else{
            int effectLines = receiptDao.updateState(receiptId , state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
            // 更新客户表(更新应付字段)
            // 更新应付 payable
            ReceiptPO receipt = receiptDao.findOneReceiptById(receiptId);
            CustomerPO customerPO = customerService.findCustomerById(receipt.getCustom());
            customerPO.setPayable(customerPO.getPayable().min(receipt.getTotalAmount()));
            customerService.updateCustomer(customerPO);
            //向财务人员发出通知（todo）

        }
    }

    /**
     * 根据id获取收款单信息
     * @param receiptId
     * @return   收款单
     */
    @Override
    public ReceiptVO getReceiptById(String receiptId){
        ReceiptPO receiptPO = receiptDao.findOneReceiptById(receiptId);
        if(receiptPO == null) return null;
        List<TransferAccountPO> accountPO = receiptDao.findTransferAccountById(receiptId);
        ReceiptVO rVO = new ReceiptVO();
        BeanUtils.copyProperties(receiptPO,rVO);
        List<TransferAccountVO> transferAccountVOList = new ArrayList<>();
        for(TransferAccountPO content : accountPO){
            TransferAccountVO tAccountVO = new TransferAccountVO();
            BeanUtils.copyProperties(content,tAccountVO);
            transferAccountVOList.add(tAccountVO);
        }
        rVO.setTransferAccountVOS(transferAccountVOList);
        return rVO;

    }

    @Override
    public String makeChange(String receiptId) {
        receiptDao.updateState(receiptId,ReceiptState.DONE);
        ReceiptPO receiptPO = receiptDao.findOneReceiptById(receiptId);
        List<TransferAccountPO> list = receiptDao.findTransferAccountById(receiptId);
        BigDecimal bigDecimal = BigDecimal.ZERO;
        for(TransferAccountPO i:list){
            BigDecimal amount = i.getAmount();
            Integer bid = i.getBid();
            BankAccountPO bankAccountPO = bankAccountDao.findByBankAccountId(bid);
            bankAccountPO.setBankAmount(bankAccountPO.getBankAmount().add(amount));
            bankAccountDao.updateByBankAccountId(bankAccountPO);
            bigDecimal = bigDecimal.add(amount);
        }
        CustomerPO customerPO = customerDao.findOneById(receiptPO.getCustom());
        customerPO.setPayable(customerPO.getPayable().subtract(bigDecimal));
        customerDao.updateOne(customerPO);
        return "操作成功";
    }
}
