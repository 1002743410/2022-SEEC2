package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.*;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.service.CheckListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckListServiceImpl implements CheckListService {
    private final CheckListDao checkListDao;
    private final SaleSheetDao saleSheetDao;
    private final SaleReturnsSheetDao saleReturnsSheetDao;
    private final PurchaseSheetDao purchaseSheetDao;
    private final PurchaseReturnsSheetDao purchaseReturnsSheetDao;

    @Autowired
    public CheckListServiceImpl(CheckListDao checkListDao, SaleSheetDao saleSheetDao, PurchaseSheetDao purchaseSheetDao, PurchaseReturnsSheetDao purchaseReturnsSheetDao,SaleReturnsSheetDao saleReturnsSheetDao) {
        this.checkListDao = checkListDao;
        this.saleSheetDao = saleSheetDao;
        this.saleReturnsSheetDao = saleReturnsSheetDao;
        this.purchaseSheetDao = purchaseSheetDao;
        this.purchaseReturnsSheetDao = purchaseReturnsSheetDao;
    }


    @Override
    public String stub1() {
        return "test.............";
    }

    @Override
    public List<CheckSaleDetailPO> getCSDByTime(String beginDateStr, String endDateStr) {
        List<CheckSaleDetailPO> list = new ArrayList<>();
        if(earlier(beginDateStr,endDateStr)==1){
            //System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111111");
            return null;
        }
        else{
            list.addAll(checkListDao.findCSDByTime(beginDateStr, endDateStr));
            list.addAll(checkListDao.findCSRDByTime(beginDateStr, endDateStr));
            return list;
        }

    }

    @Override
    public List<CheckSaleDetailPO> getCSDByName(String name) {
        List<CheckSaleDetailPO> list = new ArrayList<>();
        list.addAll(checkListDao.findCSDByName(name));
        list.addAll(checkListDao.findCSRDByName(name));
        return list;
    }

    @Override
    public List<CheckSaleDetailPO> getCSDByProduct(String product) {
        List<CheckSaleDetailPO> list = new ArrayList<>();
        list.addAll(checkListDao.findCSDByProduct(product));
        list.addAll(checkListDao.findCSRDByProduct(product));
        return list;
    }

    @Override
    public List<CheckSaleDetailPO> getCSDByCustomer(Integer id) {
        List<CheckSaleDetailPO> list = new ArrayList<>();
        list.addAll(checkListDao.findCSDByCustomer(id));
        list.addAll(checkListDao.findCSRDByCustomer(id));
        return list;
    }

    @Override
    public List<Sheets> getSheetByTime(String b, String e) {
        List<Sheets> list= new ArrayList<>();
        if(earlier(b,e)==1){
            return null;
        }
        if(saleSheetDao.getSSByTime(b,e)!=null){
            list.addAll(saleSheetDao.getSSByTime(b,e));
        }
        if(purchaseSheetDao.getPSByTime(b,e)!=null){
            list.addAll(purchaseSheetDao.getPSByTime(b,e));
        }
        if(purchaseReturnsSheetDao.getPRSByTime(b,e)!=null){
            list.addAll(purchaseReturnsSheetDao.getPRSByTime(b,e));
        }
        list.addAll(saleReturnsSheetDao.getSRSByTime(b, e));
        return list;
    }

    @Override
    public List<Sheets> getSheetByCustomer(Integer supplier) {
        List<Sheets> list= new ArrayList<>();

        if(saleSheetDao.getSSBySupplier(supplier)!=null){
            list.addAll(saleSheetDao.getSSBySupplier(supplier));
        }
        if(purchaseSheetDao.getPSBySupplier(supplier)!=null){
            list.addAll(purchaseSheetDao.getPSBySupplier(supplier));
        }
        list.addAll(purchaseReturnsSheetDao.getRPSBySupplier(supplier));
        list.addAll(saleReturnsSheetDao.getSRSBySupplier(supplier));
        return list;
    }

    @Override
    public List<Sheets> getSheetByOperator(String operator) {
        List<Sheets> list= new ArrayList<>();
        if(saleSheetDao.getSSByOperator(operator)!=null){
            list.addAll(saleSheetDao.getSSByOperator(operator));
        }
        if(purchaseSheetDao.getPSByOperator(operator)!=null){
            list.addAll(purchaseSheetDao.getPSByOperator(operator));
        }
        if(purchaseReturnsSheetDao.getPRSByOperator(operator)!=null){
            list.addAll(purchaseReturnsSheetDao.getPRSByOperator(operator));
        }
        list.addAll(saleReturnsSheetDao.getSRSByOperator(operator));
        return list;
    }

    @Override
    public List<Sheets> getSheetByClass(String type) {
        List<Sheets> list = new ArrayList<>();
        switch (type){
            case "销售单":
                list.addAll(saleSheetDao.findAllSheet());
            case "进货单":
                list.addAll(purchaseSheetDao.findAll());
            case "进货退货单":
                list.addAll(purchaseReturnsSheetDao.findAll());
            case "销售退货单":
                list.addAll(saleReturnsSheetDao.findAllReturnsSheet());
        }
        return list;
    }

    @Override
    public OpConditionPO getOpCondition(String beginDateStr, String endDateStr) {
        List<SaleSheetPO> s = saleSheetDao.getSSByTimeEX(beginDateStr, endDateStr);
        List<SaleReturnsSheetPO> sr = saleReturnsSheetDao.getSRSByTimeEX(beginDateStr, endDateStr);
        List<PurchaseSheetPO> p = purchaseSheetDao.getPSByTimeEX(beginDateStr, endDateStr);
        List<PurchaseReturnsSheetPO> pr = purchaseReturnsSheetDao.getPRSByTimeEX(beginDateStr, endDateStr);

        BigDecimal saleIncome = BigDecimal.ZERO;
        BigDecimal productIncome= new BigDecimal(0);
        BigDecimal saleOutcome= new BigDecimal(0);
        BigDecimal productOutcome= new BigDecimal(0);
        BigDecimal salaryOutcome= new BigDecimal(0);
        BigDecimal discountAmount= new BigDecimal(0);
        BigDecimal totalIncome= new BigDecimal(0);
        BigDecimal totalOutcome= new BigDecimal(0);
        BigDecimal profit= new BigDecimal(0);

        for(SaleSheetPO i:s){
           // System.out.println(i);
            //System.out.println(i.getFinalAmount());
            //System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            saleIncome = saleIncome.add(i.getFinalAmount());
            System.out.println(saleIncome);
            discountAmount=discountAmount.add(i.getRawTotalAmount().subtract(i.getFinalAmount()));
        }
        for (SaleReturnsSheetPO i:sr){
            saleIncome=saleIncome.subtract(i.getTotalAmount());
            discountAmount=discountAmount.subtract(i.getTotalAmount().multiply((BigDecimal.ONE.subtract(i.getDiscount())).divide(i.getDiscount())));
            productIncome=productIncome.add(i.getVoucherAmount());
        }
        for (PurchaseSheetPO i:p){
            saleOutcome=saleOutcome.add(i.getTotalAmount());
        }
        for (PurchaseReturnsSheetPO i:pr){
            saleOutcome=saleOutcome.subtract(i.getTotalAmount());
        }
        totalIncome=totalIncome.add(saleIncome).add(productIncome);
        totalOutcome=totalOutcome.add(saleOutcome.add(productOutcome)).add(salaryOutcome);
        profit=profit.add(totalIncome).subtract(totalOutcome);
        OpConditionPO opConditionPO = new OpConditionPO();
        opConditionPO.setDiscountAmount(discountAmount);
        opConditionPO.setProfit(profit);
        opConditionPO.setTotalIncome(totalIncome);
        opConditionPO.setTotalOutcome(totalOutcome);
        return opConditionPO;
    }

    public int earlier(String A,String B){
        for(int i = 0;i<19;i++){
            if(A.charAt(i)>B.charAt(i)){
                return 1;
            }
            else if(A.charAt(i)<B.charAt(i)){
                return 0;
            }
        }
        return 0;
    }

}
