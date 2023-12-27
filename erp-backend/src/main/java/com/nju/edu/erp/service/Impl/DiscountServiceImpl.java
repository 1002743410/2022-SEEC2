package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.DiscountDao;
import com.nju.edu.erp.model.po.DiscountPO;
import com.nju.edu.erp.model.po.ProductPO;
import com.nju.edu.erp.model.po.SaleSheetContentPO;
import com.nju.edu.erp.model.vo.discount.DiscountVO;
import com.nju.edu.erp.model.vo.discount.bagVO;
import com.nju.edu.erp.service.DiscountService;
import com.nju.edu.erp.service.DiscountStrategy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DiscountServiceImpl  implements DiscountService {
    private final DiscountDao discountDao;
    @Autowired
    public DiscountServiceImpl(DiscountDao discountDao){
        this.discountDao = discountDao;
    }
    DiscountStrategy discountStrategy;

    public void setDiscount(DiscountVO discountVO){
        DiscountPO discountPO = new DiscountPO();
        BeanUtils.copyProperties(discountVO,discountPO);
        discountPO.setCreateTime(new Date());
        switch (discountPO.getType()){
            case 1:
                discountStrategy = new DStrategy1(discountDao);
                break;
            case 2:
            {
                DStrategy2 dStrategy2= new DStrategy2(discountDao);
                dStrategy2.setBagContent(discountVO.getBag());
                discountStrategy = dStrategy2;
            }
            break;
            case 3:
                discountStrategy = new DStrategy3(discountDao);
        }
        discountStrategy.makeDiscount(discountPO);
    }

    @Override
    public void deleteDiscount(Integer id) {
        discountDao.deleteDiscount(id);
    }

    @Override
    public List<DiscountVO> findAll() {
        List<DiscountVO> list = new ArrayList<>();
        List<DiscountPO> list1 = discountDao.getAll();
        for(DiscountPO discountPO:list1){
            DiscountVO discountVO = new DiscountVO();
            BeanUtils.copyProperties(discountPO,discountVO);
            if(discountVO.getType()==2){
                List<String> list2 = discountDao.getBagContent(discountVO.getId());
                List<bagVO> list3 = new ArrayList<>();
                for(String i:list2){
                    bagVO b = new bagVO();
                    b.setPid(i);
                    list3.add(b);
                }
                discountVO.setBag(list3);
            }
            list.add(discountVO);
        }
        return list;
    }

    @Override
    public List<BigDecimal> chooseDiscount(Integer level, List<SaleSheetContentPO> saleContent, BigDecimal totalAmount) {
        List<BigDecimal> list = new ArrayList<>();
        DiscountPO one = new DiscountPO();
        DiscountPO three = new DiscountPO();
        if(discountDao.getByLevel(level)!=null){
            one = discountDao.getByLevel(level);
        }
        else {
            one.setDiscount(BigDecimal.valueOf(1));
            one.setVoucherAmount(BigDecimal.valueOf(0));
        }
        if(discountDao.getMax(totalAmount)!=null){
            three = discountDao.getMax(totalAmount);
        }
        else {
            three.setDiscount(BigDecimal.valueOf(1));
            three.setVoucherAmount(BigDecimal.valueOf(0));
        }
        if(totalAmount.multiply(one.getDiscount()).subtract(one.getVoucherAmount()).compareTo(totalAmount.multiply(three.getDiscount()).subtract(three.getVoucherAmount()))!=1){
            list.add(0,one.getDiscount());
            list.add(1,one.getVoucherAmount());
        }
        else {
            list.add(0,three.getDiscount());
            list.add(1,three.getVoucherAmount());
        }

        //list1存放传进来的商品id
        List<String> list1 = new ArrayList<>();
        for(SaleSheetContentPO item :saleContent){
            list1.add(item.getPid());
        }
        //list2存放第二类折扣
        List<Integer> list2 = discountDao.getIdOfSt2();
        Integer myId = 0;
        for(Integer i:list2){
            List<String> list3=discountDao.getBagContent(i);
            if(list3!=null){
                if(list3.containsAll(list1)&&list1.containsAll(list3)){
                    myId = i;
                    break;
                }
            }
        }
        if(myId==0){
            return list;
        }
        else {
            DiscountPO two = discountDao.getById(myId);
            if(totalAmount.multiply(two.getDiscount()).subtract(two.getVoucherAmount()).compareTo(totalAmount.multiply(list.get(0)).subtract(list.get(1)))!=1){
                list.set(0,two.getDiscount());
                list.set(1,two.getVoucherAmount());
            }
        }
        return list;
    }


}
