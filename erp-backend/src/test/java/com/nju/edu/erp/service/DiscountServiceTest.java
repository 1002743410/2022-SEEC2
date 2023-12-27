package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.DiscountDao;
import com.nju.edu.erp.model.vo.discount.DiscountVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;

@SpringBootTest
public class DiscountServiceTest {
    @Autowired
    DiscountDao discountDao;

    @Autowired
    DiscountService discountService;
    @Test
    public void add(){
        DiscountVO discountVO1 = new DiscountVO();
        DiscountVO discountVO2 = new DiscountVO();

        discountVO1.setType(1);
        discountVO1.setCustomerLevel(3);
        discountVO1.setDiscount(BigDecimal.valueOf(0.8));
        discountVO1.setVoucherAmount(BigDecimal.valueOf(300));

        discountVO2.setType(3);
        discountVO2.setTotalAmount(BigDecimal.valueOf(1000000));
        discountVO2.setDiscount(BigDecimal.valueOf(0.8));
        discountVO2.setVoucherAmount(BigDecimal.valueOf(300));

        discountService.setDiscount(discountVO1);
        discountService.setDiscount(discountVO2);
    }

    @Test
    public void update(){
        DiscountVO discountVO1 = new DiscountVO();
        DiscountVO discountVO2 = new DiscountVO();

        discountVO1.setType(1);
        discountVO1.setCustomerLevel(3);
        discountVO1.setDiscount(BigDecimal.valueOf(0.85));
        discountVO1.setVoucherAmount(BigDecimal.valueOf(200));

        discountVO2.setType(3);
        discountVO2.setTotalAmount(BigDecimal.valueOf(1000000));
        discountVO2.setDiscount(BigDecimal.valueOf(0.85));
        discountVO2.setVoucherAmount(BigDecimal.valueOf(300));

        discountService.setDiscount(discountVO1);
        discountService.setDiscount(discountVO2);
    }

    @Test
    public void smaller(){
        DiscountVO discountVO1 = new DiscountVO();
        DiscountVO discountVO2 = new DiscountVO();

        discountVO1.setType(1);
        discountVO1.setCustomerLevel(5);
        discountVO1.setDiscount(BigDecimal.valueOf(0.9));
        discountVO1.setVoucherAmount(BigDecimal.valueOf(300));

        discountVO2.setType(3);
        discountVO2.setTotalAmount(BigDecimal.valueOf(10000000));
        discountVO2.setDiscount(BigDecimal.valueOf(0.9));
        discountVO2.setVoucherAmount(BigDecimal.valueOf(300));

        discountService.setDiscount(discountVO1);
        discountService.setDiscount(discountVO2);
    }

    @Test
    public void bigger(){
        DiscountVO discountVO1 = new DiscountVO();
        discountVO1.setType(3);
        discountVO1.setDiscount(BigDecimal.valueOf(0.75));
        discountVO1.setTotalAmount(BigDecimal.valueOf(1000));
        discountService.setDiscount(discountVO1);

    }


}
