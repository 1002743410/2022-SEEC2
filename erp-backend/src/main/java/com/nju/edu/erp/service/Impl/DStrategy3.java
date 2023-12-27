package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.DiscountDao;
import com.nju.edu.erp.model.po.DiscountPO;
import com.nju.edu.erp.service.DiscountStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public class DStrategy3 implements DiscountStrategy {
    private final DiscountDao discountDao;

    public DStrategy3(DiscountDao discountDao){
        this.discountDao = discountDao;
    }
    @Override
    public void makeDiscount(DiscountPO discountPO) {

        BigDecimal discount = discountPO.getDiscount();
        BigDecimal v = discountPO.getVoucherAmount();
        BigDecimal amount = discountPO.getTotalAmount();
        List<DiscountPO> list = discountDao.getLSTA(amount);
        List<DiscountPO> list1 = discountDao.getBGTA(amount);
        //保证不会比比自己价格低的折扣低
        if(list!=null){
            for (DiscountPO i:list){
                if(i.getDiscount().compareTo(discount)==-1){
                    discount = i.getDiscount();
                }
                if(i.getVoucherAmount()!=null&&i.getVoucherAmount().compareTo(v)==1){
                    v = i.getVoucherAmount();
                }
            }
        }
        //保证不会比比自己价格高的折扣高
        if(list1!=null){
            for (DiscountPO i:list1){
                if(i.getDiscount().compareTo(discount)==1){
                    discount = i.getDiscount();
                    System.out.println("11111111111111111111111111111111111111111");
                    System.out.println(discount);
                }
                if(v!=null&&i.getVoucherAmount().compareTo(v)==-1){
                    v = i.getVoucherAmount();
                }
            }
        }
        discountPO.setDiscount(discount);
        discountPO.setVoucherAmount(v);
        if(discountDao.getByAmount(amount)!=null){
            discountDao.updateByAmount(discountPO);
        }
        else {
            discountDao.createDiscount(discountPO);
        }
    }
}
