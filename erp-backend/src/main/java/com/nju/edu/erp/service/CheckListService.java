package com.nju.edu.erp.service;

import com.nju.edu.erp.model.po.CheckSaleDetailPO;
import com.nju.edu.erp.model.po.OpConditionPO;
import com.nju.edu.erp.model.po.Sheets;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CheckListService {
    //桩程序
    String stub1();
    //查看销售明细表
    List<CheckSaleDetailPO> getCSDByTime(String beginDateStr,String endDateStr);
    List<CheckSaleDetailPO> getCSDByName(String name);
    List<CheckSaleDetailPO> getCSDByProduct(String product);
    List<CheckSaleDetailPO> getCSDByCustomer(Integer id);
    //查看经营历程表
    List<Sheets> getSheetByTime(String b,String e);
    List<Sheets> getSheetByCustomer(Integer supplier);
    List<Sheets> getSheetByOperator(String operator);
    List<Sheets> getSheetByClass(String type);
    //查看销售情况表
    OpConditionPO getOpCondition(String beginDateStr,String endDateStr);
}
