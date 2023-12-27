package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.DiscountDao;
import com.nju.edu.erp.model.po.DiscountPO;
import com.nju.edu.erp.service.DiscountStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


public class DStrategy1 implements DiscountStrategy {
    private final DiscountDao discountDao;

    public DStrategy1(DiscountDao discountDao){
        this.discountDao = discountDao;
    }

    @Override
    public void makeDiscount(DiscountPO discountPO) {
        BigDecimal discount = discountPO.getDiscount();
        BigDecimal v = discountPO.getVoucherAmount();
        Integer level = discountPO.getCustomerLevel();
        List<DiscountPO> list = discountDao.getLSTL(level);
        List<DiscountPO> list1 = discountDao.getBGTL(level);
        //保证不会比比自己低级的折扣低
        if(list!=null){
            for (DiscountPO i:list){
                if(i.getDiscount().compareTo(discount)==-1){
                    discount = i.getDiscount();
                }
                if(i.getVoucherAmount().compareTo(v)==1){
                    v = i.getVoucherAmount();
                }
            }
        }
        //保证不会比比自己高级的折扣高
        if(list1!=null){
            for (DiscountPO i:list1){
                if(i.getDiscount().compareTo(discount)==1){
                    discount = i.getDiscount();
                }
                if(i.getVoucherAmount().compareTo(v)==-1){
                    v = i.getVoucherAmount();
                }
            }
        }
        discountPO.setDiscount(discount);
        discountPO.setVoucherAmount(v);
        if(discountDao.getByLevel(level)!=null){
            System.out.println("1111111111111111111111111111111111111111111111111111111111");
            discountDao.updateByLevel(discountPO);
        }
        else {
            System.out.println("2222222222222222222222222222222222222222222222222222222222222222");
            discountDao.createDiscount(discountPO);
        }
    }
}
