package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.CustomerDao;
import com.nju.edu.erp.dao.DiscountDao;
import com.nju.edu.erp.dao.ProductDao;
import com.nju.edu.erp.dao.SaleSheetDao;
import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetContentVO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetContentVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseInputFormContentVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseInputFormVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseOutputFormContentVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseOutputFormVO;
import com.nju.edu.erp.service.*;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleSheetDao saleSheetDao;

    private final ProductDao productDao;

    private final CustomerDao customerDao;

    private final ProductService productService;

    private final CustomerService customerService;

    private final WarehouseService warehouseService;

    private final DiscountService discountService;

    @Autowired
    public SaleServiceImpl(SaleSheetDao saleSheetDao, ProductDao productDao, CustomerDao customerDao, ProductService productService, CustomerService customerService, WarehouseService warehouseService,DiscountService discountService) {
        this.saleSheetDao = saleSheetDao;
        this.productDao = productDao;
        this.customerDao = customerDao;
        this.productService = productService;
        this.customerService = customerService;
        this.warehouseService = warehouseService;
        this.discountService = discountService;
    }

    @Override
    @Transactional
    public void makeSaleSheet(UserVO userVO, SaleSheetVO saleSheetVO) {
        // TODO
        // 需要持久化销售单（SaleSheet）和销售单content（SaleSheetContent），其中打折、抵价券、总价或者折后价格的计算需要在后端进行
        // 需要的service和dao层相关方法均已提供，可以不用自己再实现一遍
        SaleSheetPO saleSheetPO = new SaleSheetPO();
        BeanUtils.copyProperties(saleSheetVO, saleSheetPO);
        // 此处根据制定单据人员确定操作员
        saleSheetPO.setOperator(userVO.getName());
        saleSheetPO.setCreateTime(new Date());
        SaleSheetPO latest = saleSheetDao.getLatestSheet();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "XSD");
        saleSheetPO.setId(id);
        saleSheetPO.setState(SaleSheetState.PENDING_LEVEL_1);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<SaleSheetContentPO> sContentPOList = new ArrayList<>();
        for(SaleSheetContentVO content : saleSheetVO.getSaleSheetContent()) {
            SaleSheetContentPO sContentPO = new SaleSheetContentPO();
            BeanUtils.copyProperties(content,sContentPO);

            sContentPO.setSaleSheetId(id);

            BigDecimal unitPrice = sContentPO.getUnitPrice();
            if(unitPrice == null) {
                ProductPO product = productDao.findById(content.getPid());
                unitPrice = product.getPurchasePrice();
                sContentPO.setUnitPrice(unitPrice);
            }
            sContentPO.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(sContentPO.getQuantity())));
            sContentPOList.add(sContentPO);
            totalAmount = totalAmount.add(sContentPO.getTotalPrice());
        }
        saleSheetDao.saveBatchSheetContent(sContentPOList);
        saleSheetPO.setRawTotalAmount(totalAmount);
        //获取折扣和使用代金券金额---》更改为自己计算
        //BigDecimal dis = saleSheetPO.getDiscount();
        //BigDecimal vouAmount = saleSheetPO.getVoucherAmount();
        //BigDecimal final_amount = totalAmount.multiply(dis).subtract(vouAmount);
        //saleSheetPO.setFinalAmount(final_amount);
        List<BigDecimal> list =
                discountService.chooseDiscount(customerService.getLevel(saleSheetPO.getSupplier()),
                        sContentPOList,totalAmount);
        saleSheetPO.setDiscount(list.get(0));
        saleSheetPO.setVoucherAmount(list.get(1));
        BigDecimal final_amount = totalAmount.multiply(list.get(0)).subtract(list.get(1));
        saleSheetPO.setFinalAmount(final_amount);
        saleSheetDao.saveSheet(saleSheetPO);
    }

    @Override
    @Transactional
    public List<SaleSheetVO> getSaleSheetByState(SaleSheetState state) {
        // TODO
        // 根据单据状态获取销售单（注意：VO包含SaleSheetContent）
        // 依赖的dao层部分方法未提供，需要自己实现
        List<SaleSheetVO> res = new ArrayList<>();
        List<SaleSheetPO> all;
        if(state == null) {
            all = saleSheetDao.findAllSheet();
        } else {
            all = saleSheetDao.findallByState(state);
        }
        for(SaleSheetPO po: all) {
            SaleSheetVO vo = new SaleSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<SaleSheetContentPO> alll = saleSheetDao.findContentBySheetId(po.getId());
            List<SaleSheetContentVO> vos = new ArrayList<>();
            for (SaleSheetContentPO p : alll) {
                SaleSheetContentVO v = new SaleSheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setSaleSheetContent(vos);
            res.add(vo);
        }
        return res;

    }

    /**
     * 根据销售单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param saleSheetId 销售单id
     * @param state       销售单要达到的状态
     */
    @Override
    @Transactional
    public void approval(String saleSheetId, SaleSheetState state) {
        // TODO
        // 需要的service和dao层相关方法均已提供，可以不用自己再实现一遍
        /* 一些注意点：
            1. 二级审批成功之后需要进行
                 1. 修改单据状态
                 2. 更新商品表
                 3. 更新客户表
                 4. 新建出库草稿
            2. 一级审批状态不能直接到审批完成状态； 二级审批状态不能回到一级审批状态
         */
        if(state.equals(SaleSheetState.FAILURE)) {
            SaleSheetPO saleSheet = saleSheetDao.findSheetById(saleSheetId);
            if(saleSheet.getState() == SaleSheetState.SUCCESS) throw new RuntimeException("状态更新失败");
            int effectLines = saleSheetDao.updateSheetState(saleSheetId, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
        } else {
            SaleSheetState prevState;
            if(state.equals(SaleSheetState.SUCCESS)) {
                prevState = SaleSheetState.PENDING_LEVEL_2;
            } else if(state.equals(SaleSheetState.PENDING_LEVEL_2)) {
                prevState = SaleSheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = saleSheetDao.updateSheetStateOnPrev(saleSheetId, prevState, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
            if(state.equals(SaleSheetState.SUCCESS)) {
                // TODO 审批完成, 修改一系列状态
                // 更新商品表的最新进价
                // 根据purchaseSheetId查到对应的content -> 得到商品id和单价
                // 根据商品id和单价更新商品最近进价recentPp
                List<SaleSheetContentPO> saleSheetContent =  saleSheetDao.findContentBySheetId(saleSheetId);
                List<WarehouseOutputFormContentVO> warehouseOutputFormContentVOS = new ArrayList<>();

                for(SaleSheetContentPO content : saleSheetContent) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    String tt = content.getPid();
                    productInfoVO.setId(tt);
                    BigDecimal t = content.getUnitPrice();
                    productInfoVO.setRecentRp(t);
                    productService.updateProduct(productInfoVO);

                    WarehouseOutputFormContentVO woContentVO = new WarehouseOutputFormContentVO();
                    woContentVO.setSalePrice(content.getUnitPrice());
                    woContentVO.setQuantity(content.getQuantity());
                    woContentVO.setRemark(content.getRemark());
                    woContentVO.setPid(content.getPid());
                    warehouseOutputFormContentVOS.add(woContentVO);
                }
                // 更新客户表(更新应付字段)
                // 更新应付 payable
                SaleSheetPO saleSheet = saleSheetDao.findSheetById(saleSheetId);
                CustomerPO customerPO = customerService.findCustomerById(saleSheet.getSupplier());
                customerPO.setReceivable(customerPO.getReceivable().add(saleSheet.getFinalAmount()));
                customerService.updateCustomer(customerPO);

                // 制定入库单草稿(在这里关联进货单)
                // 调用创建入库单的方法
                WarehouseOutputFormVO warehouseOutputFormVO = new WarehouseOutputFormVO();
                warehouseOutputFormVO.setOperator(null); // 暂时不填操作人(确认草稿单的时候填写)
                warehouseOutputFormVO.setSaleSheetId(saleSheetId);
                warehouseOutputFormVO.setList(warehouseOutputFormContentVOS);
                warehouseService.productOutOfWarehouse(warehouseOutputFormVO);
            }
        }
    }

    /**
     * 获取某个销售人员某段时间内消费总金额最大的客户(不考虑退货情况,销售单不需要审批通过,如果这样的客户有多个，仅保留一个)
     * @param salesman 销售人员的名字
     * @param beginDateStr 开始时间字符串
     * @param endDateStr 结束时间字符串
     * @return
     */
    public CustomerPurchaseAmountPO getMaxAmountCustomerOfSalesmanByTime(String salesman,String beginDateStr,String endDateStr){
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date beginTime =dateFormat.parse(beginDateStr);
            Date endTime=dateFormat.parse(endDateStr);
            if(beginTime.compareTo(endTime)>0){
                return null;
            }else{
                return saleSheetDao.getMaxAmountCustomerOfSalesmanByTime(salesman,beginTime,endTime);
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据销售单Id搜索销售单信息
     * @param saleSheetId 销售单Id
     * @return 销售单全部信息
     */
    @Override
    public SaleSheetVO getSaleSheetById(String saleSheetId) {
        SaleSheetPO saleSheetPO = saleSheetDao.findSheetById(saleSheetId);
        if(saleSheetPO == null) return null;
        List<SaleSheetContentPO> contentPO = saleSheetDao.findContentBySheetId(saleSheetId);
        SaleSheetVO sVO = new SaleSheetVO();
        BeanUtils.copyProperties(saleSheetPO, sVO);
        List<SaleSheetContentVO> saleSheetContentVOList = new ArrayList<>();
        for (SaleSheetContentPO content:
                contentPO) {
            SaleSheetContentVO sContentVO = new SaleSheetContentVO();
            BeanUtils.copyProperties(content, sContentVO);
            saleSheetContentVOList.add(sContentVO);
        }
        sVO.setSaleSheetContent(saleSheetContentVOList);
        return sVO;
    }
}
