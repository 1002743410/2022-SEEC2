package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.DiscountDao;
import com.nju.edu.erp.model.po.DiscountPO;
import com.nju.edu.erp.model.vo.discount.bagVO;
import com.nju.edu.erp.service.DiscountStrategy;

import java.util.ArrayList;
import java.util.List;

public class DStrategy2 implements DiscountStrategy {
    private final DiscountDao discountDao;
    private List<String> bagContent = new ArrayList<>();

    public DStrategy2(DiscountDao discountDao){
        this.discountDao = discountDao;
    }

    void setBagContent(List<bagVO> list){
        for(bagVO i:list){
            bagContent.add(i.getPid());
        }
    }

    @Override
    public void makeDiscount(DiscountPO discountPO) {
        List<Integer> list = discountDao.getIdOfSt2();
        Integer myId = 0;
        for(Integer i:list){
            List<String> list1=discountDao.getBagContent(i);
            if(list1!=null&&bagContent!=null){
                if(list1.containsAll(bagContent)&&bagContent.containsAll(list1)){
                    myId = i;
                    break;
                }
            }
        }
        if(myId!=0){
            discountPO.setId(myId);
            discountDao.updateById(discountPO);
        }
        else {
            discountDao.createDiscount(discountPO);
            Integer id = discountDao.getLatest();
            for (String i:bagContent){
                discountDao.addBagContent(id,i);
            }
        }
    }
}
