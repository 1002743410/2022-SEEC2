package com.nju.edu.erp.service;




import com.nju.edu.erp.model.po.BankAccountPO;
import com.nju.edu.erp.model.po.PaymentOrderPO;
import com.nju.edu.erp.model.po.ReceiptPO;
import com.nju.edu.erp.model.po.WarehouseIODetailPO;
import com.nju.edu.erp.model.vo.finance.PaymentOrderVO;
import com.nju.edu.erp.model.vo.finance.ReceiptVO;
import com.nju.edu.erp.model.vo.finance.TermSheetVO;
import com.nju.edu.erp.model.vo.finance.TransferAccountVO;
import com.nju.edu.erp.web.controller.CheckListController;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class WarehouseServiceTest {

    @Autowired
    WarehouseService warehouseService;
    @Autowired
    CheckListService checkListService;
    @Autowired
    CheckListController checkListController;
    @Autowired
    ReceiptService receiptService;
    @Autowired
    PaymentOrderService paymentOrderService;
    @Autowired
    BankAccountService bankAccountService;

    @Test
    @Transactional
    @Rollback
    public void getWarehouseIODetailByTime_TimeError() throws ParseException { //测试库存查看，当开始时间大于结束时间时，库存查看的结果应为null
        List<WarehouseIODetailPO> warehouseIODetailPOList=warehouseService.getWarehouseIODetailByTime("2022-05-25 00:00:00","2022-05-24 00:00:00");
        Assertions.assertNull(warehouseIODetailPOList);
    }

    //查看三种表的测试
    @Test
    public void driven1() throws ParseException{
        //System.out.println(checkListService.getCSDByTime("2022-05-23 23:17:40","2022-07-25 00:33:12").get(0).getCreateTime());
        //System.out.println(checkListService.getCSDByName("xiaoshoujingli"));
        //System.out.println(checkListService.getCSDByProduct("戴尔电脑"));
        //System.out.println(checkListService.getCSDByCustomer(2));
        //System.out.println(checkListService.getSheetByTime("2022-05-23 23:17:40","2022-05-25 00:33:12"));
        //System.out.println(checkListService.getSheetByCustomer(2));
        //System.out.println(checkListService.getSheetByOperator("xiaoshoujingli"));
        //System.out.println(checkListController.getSaleDetailByTime("2022-05-23 23:17:40","2022-07-25 00:33:12").getResult());
        //System.out.println(checkListService.getOpCondition("2022-05-23 23:17:40","2022-07-25 00:33:12"));
        //System.out.println("1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        String a = "SKD-20220709-00000";
        System.out.println(receiptService.makeChange(a));

    }

    @Test
    @Transactional
    @Rollback
    public void getWarehouseIODetailByTime_EmptyResult() throws ParseException { //测试库存查看，指定时间段内没有出入库记录，库存查看的结果应为空数组[]
        List<WarehouseIODetailPO> warehouseIODetailPOList=warehouseService.getWarehouseIODetailByTime("2022-05-25 00:00:00","2022-05-25 23:59:59");
        Assertions.assertNotNull(warehouseIODetailPOList);
        Assertions.assertEquals(0,warehouseIODetailPOList.size());

    }

    @Test
    @Transactional
    @Rollback
    public void getWarehouseIODetailByTime() throws ParseException { //测试库存查看,有记录的情况
        List<WarehouseIODetailPO> warehouseIODetailPOList=warehouseService.getWarehouseIODetailByTime("2022-05-23 23:17:40","2022-05-24 00:33:12");
        Assertions.assertNotNull(warehouseIODetailPOList);
        Assertions.assertEquals(10,warehouseIODetailPOList.size());

    }

    @Test
    @Transactional
    @Rollback
    public void getWarehouseInputProductQuantityByTime_TimeError(){ //测试查看一段时间内入库商品的数量,当开始时间大于结束时间时，数量应为0
        int quantity=warehouseService.getWarehouseInputProductQuantityByTime("2022-05-25 00:00:00","2022-05-24 00:00:00");
        Assertions.assertEquals(0,quantity);
    }

    @Test
    @Transactional
    @Rollback
    public void getWarehouseInputProductQuantityByTime_EmptyResult(){ //测试查看一段时间内入库商品的数量,当指定时间段没有入库商品时，数量应为0
        int quantity=warehouseService.getWarehouseInputProductQuantityByTime("2022-05-25 00:00:00","2022-05-25 23:59:59");
        Assertions.assertEquals(0,quantity);
    }

    @Test
    @Transactional
    @Rollback
    public void getWarehouseInputProductQuantityByTime(){ //测试查看一段时间内入库商品的数量,这段时间内有入库商品的情况
        int quantity=warehouseService.getWarehouseInputProductQuantityByTime("2022-05-23 23:17:40","2022-05-24 00:33:12");
        Assertions.assertEquals(4000,quantity);
    }

    @Test
    @Transactional
    @Rollback
    public void getWarehouseOutProductQuantityByTime_TimeError(){ //测试查看一段时间内出库商品的数量,当开始时间大于结束时间时，数量应为0
        int quantity=warehouseService.getWarehouseOutProductQuantityByTime("2022-05-25 00:00:00","2022-05-24 00:00:00");
        Assertions.assertEquals(0,quantity);
    }

    @Test
    @Transactional
    @Rollback
    public void getWarehouseOutProductQuantityByTime_EmptyResult(){ //测试查看一段时间内出库商品的数量,当指定时间段没有入库商品时，数量应为0
        int quantity=warehouseService.getWarehouseOutProductQuantityByTime("2022-05-25 00:00:00","2022-05-25 23:59:59");
        Assertions.assertEquals(0,quantity);
    }

    @Test
    @Transactional
    @Rollback
    public void getWarehouseOutProductQuantityByTime(){  //测试查看一段时间内出库商品的数量,这段时间内有出库商品的情况
        int quantity=warehouseService.getWarehouseOutProductQuantityByTime("2022-05-23 23:17:40","2022-05-24 00:33:12");
        Assertions.assertEquals(2000,quantity);
    }
    @Test
    public void a(){
        List<TransferAccountVO> a = new ArrayList<>();
        ReceiptVO receiptVO = new ReceiptVO();
        receiptVO.setCustom(1);
        receiptVO.setOperator("2");
        receiptVO.setTransferAccountVOS(a);
        receiptService.makeReceipt(receiptVO);

    }
    @Test
    public void b(){
        List<TermSheetVO> b = new ArrayList<>();
        PaymentOrderVO paymentOrderVO = new PaymentOrderVO();
        paymentOrderVO.setBid(1);
        paymentOrderVO.setId("1");
        paymentOrderVO.setCustom(1);
        paymentOrderVO.setTermSheetVOS(b);
        paymentOrderService.makePaymentOrder(paymentOrderVO);
    }

}
