package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.BankAccountDao;
import com.nju.edu.erp.dao.CustomerDao;
import com.nju.edu.erp.dao.PaymentOrderDao;
import com.nju.edu.erp.enums.sheetState.PaymentOrderState;
import com.nju.edu.erp.model.po.BankAccountPO;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.po.PaymentOrderPO;
import com.nju.edu.erp.model.po.TermSheetPO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.finance.PaymentOrderVO;
import com.nju.edu.erp.model.vo.finance.TermSheetVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.PaymentOrderService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentOrderImpl implements PaymentOrderService {
    PaymentOrderDao paymentOrderDao;
    CustomerService customerService;
    CustomerDao customerDao;
    BankAccountDao bankAccountDao;

    @Autowired
    public PaymentOrderImpl(PaymentOrderDao paymentOrderDao, CustomerService customerService, CustomerDao customerDao,BankAccountDao bankAccountDao){
        this.paymentOrderDao = paymentOrderDao;
        this.customerService = customerService;
        this.customerDao = customerDao;
        this.bankAccountDao = bankAccountDao;
    }

    /**
     * 制定付款单
     * @param
     * @param paymentOrderVO
     */
    @Override
    @Transactional
    public void makePaymentOrder(PaymentOrderVO paymentOrderVO){
        PaymentOrderPO paymentOrderPO = new PaymentOrderPO();
        BeanUtils.copyProperties(paymentOrderVO,paymentOrderPO);
        // 此处根据制定单据人员确定操作员

        PaymentOrderPO latest = paymentOrderDao.getLatestPaymentOrder();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "XJFYD");
        paymentOrderPO.setId(id);
        paymentOrderPO.setState(PaymentOrderState.PENDING);

        BigDecimal totalAmount = BigDecimal.ZERO;

        List<TermSheetPO> termSheetPOList = new ArrayList<>();
        for(TermSheetVO content:paymentOrderVO.getTermSheetVOS()){
            TermSheetPO termSheetPO = new TermSheetPO();
            BeanUtils.copyProperties(content,termSheetPO);
            termSheetPO.setPaymentOrderId(id);
            BigDecimal amount = termSheetPO.getAmount();
            termSheetPOList.add(termSheetPO);
            totalAmount = totalAmount.add(amount);
        }
        for(TermSheetPO i: termSheetPOList){
            paymentOrderDao.saveTermBatch(i);
        }
        paymentOrderPO.setTotalAmount(totalAmount);
        paymentOrderDao.savePaymentOrder(paymentOrderPO);
    }

    /**
     * 根据状态获取付款单
     * @param state
     * @return   付款单列表
     */
    @Override
    public List<PaymentOrderVO> findPaymentOrderByState(PaymentOrderState state){
        List<PaymentOrderVO> res = new ArrayList<>();
        List<PaymentOrderPO> all;
        if(state == null){
            all = paymentOrderDao.findAllPaymentOrder();
        }else{
            all = paymentOrderDao.findPaymentOrderByState(state);
        }
        for(PaymentOrderPO po : all){
            PaymentOrderVO vo = new PaymentOrderVO();
            BeanUtils.copyProperties(po, vo);
            List<TermSheetPO> alll = paymentOrderDao.findTermSheetById(po.getId());
            List<TermSheetVO> vos = new ArrayList<>();
            for(TermSheetPO p : alll){
                TermSheetVO v = new TermSheetVO();
                BeanUtils.copyProperties(p,v);
                vos.add(v);
            }
            vo.setTermSheetVOS(vos);
            res.add(vo);
        }
        return res;
    }

    /**
     * 单据审批
     * @param paymentOrderId
     * @param state 审核完之后的状态
     */
    @Override
    @Transactional
    public void approval(String paymentOrderId, PaymentOrderState state){
        if(state.equals(PaymentOrderState.FAILURE)) {
            PaymentOrderPO paymentOrder = paymentOrderDao.findOnePaymentOrderById(paymentOrderId);
            if(paymentOrder.getState() == PaymentOrderState.SUCCESS) throw new RuntimeException("状态更新失败");
            int effectLines = paymentOrderDao.updateState(paymentOrderId,state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
        }
        else{
            int effectLines = paymentOrderDao.updateState(paymentOrderId , state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
            // 更新客户表(更新应收字段)
            // 更新应收
            PaymentOrderPO paymentOrder = paymentOrderDao.findOnePaymentOrderById(paymentOrderId);
            CustomerPO customerPO = customerService.findCustomerById(paymentOrder.getCustom());
            customerPO.setReceivable(customerPO.getReceivable().min(paymentOrder.getTotalAmount()));
            customerService.updateCustomer(customerPO);

            //向财务人员发出通知（todo）

        }
    }

    /**
     * 根据id获取付款单信息
     * @param paymentOrderId
     * @return   付款单
     */
    @Override
    public PaymentOrderVO findPaymentOrderById(String paymentOrderId){
        PaymentOrderPO paymentOrderPO = paymentOrderDao.findOnePaymentOrderById(paymentOrderId);
        if(paymentOrderPO == null) return null;
        List<TermSheetPO> termPO = paymentOrderDao.findTermSheetById(paymentOrderId);
        PaymentOrderVO pVO = new PaymentOrderVO();
        BeanUtils.copyProperties(paymentOrderPO,pVO);
        List<TermSheetVO> termSheetVOList = new ArrayList<>();
        for(TermSheetPO content : termPO){
            TermSheetVO tSheetVO = new TermSheetVO();
            BeanUtils.copyProperties(content,tSheetVO);
            termSheetVOList.add(tSheetVO);
        }
        pVO.setTermSheetVOS(termSheetVOList);
        return pVO;
    }

    @Override
    public String makeChange(String paymentOrderId) {
        paymentOrderDao.updateState(paymentOrderId,PaymentOrderState.DONE);
        PaymentOrderPO paymentOrderPO = paymentOrderDao.findOnePaymentOrderById(paymentOrderId);
        Integer bid = paymentOrderPO.getBid();
        Integer custom = paymentOrderPO.getCustom();
        BigDecimal amount = paymentOrderPO.getTotalAmount();
        System.out.println(amount);
        if(amount.compareTo(bankAccountDao.getAmountById(bid))==1){
            return "操作失败：余额不足";
        }
        else {
            CustomerPO customerPO = customerDao.findOneById(custom);
            customerPO.setReceivable(customerPO.getReceivable().subtract(amount));
            customerDao.updateOne(customerPO);
            BankAccountPO bankAccountPO= bankAccountDao.findByBankAccountId(bid);
            bankAccountPO.setBankAmount(bankAccountPO.getBankAmount().subtract(amount));
            System.out.println("11111111");
            System.out.println("11111111");
            System.out.println("11111111");
            System.out.println(bankAccountPO.getBankAmount());
            bankAccountDao.updateByBankAccountId(bankAccountPO);
            return "操作成功";
        }
    }

}
