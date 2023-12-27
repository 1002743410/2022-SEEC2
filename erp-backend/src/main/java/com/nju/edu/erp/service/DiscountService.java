package com.nju.edu.erp.service;

import com.nju.edu.erp.model.po.DiscountPO;
import com.nju.edu.erp.model.po.ProductPO;
import com.nju.edu.erp.model.po.SaleSheetContentPO;
import com.nju.edu.erp.model.vo.discount.DiscountVO;

import java.math.BigDecimal;
import java.util.List;

public interface DiscountService {
    public void setDiscount(DiscountVO discountVO);
    public void deleteDiscount(Integer id);
    public List<DiscountVO> findAll();
    public List<BigDecimal> chooseDiscount(Integer level, List<SaleSheetContentPO> saleContent, BigDecimal totalAmount);
}
