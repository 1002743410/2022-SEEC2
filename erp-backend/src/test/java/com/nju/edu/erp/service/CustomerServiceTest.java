package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.CustomerDao;
import com.nju.edu.erp.model.vo.CustomerVO;
import com.nju.edu.erp.web.controller.CustomerController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class CustomerServiceTest {
    @Autowired
    CustomerDao customerDao;
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerController customerController;
    @Test
    public void addTest(){
        CustomerVO customerVO = CustomerVO.builder().id(3).type("供应商").level(3).name("mzk").phone("123456").address("南京大学").
                zipcode("220000").email("133@qq.com").lineOfCredit(BigDecimal.valueOf(12321314.000)).receivable(BigDecimal.valueOf(122.0)).
                payable(BigDecimal.valueOf(222.00)).operator("pjy").build();
        customerController.addCustomer(customerVO);
        Assertions.assertNotNull(customerService.findCustomerById(3));
    }
    @Test
    public void deleteTest(){
        customerController.deleteCustomer(3);
        Assertions.assertNull(customerDao.findOneById(3));
    }
}
