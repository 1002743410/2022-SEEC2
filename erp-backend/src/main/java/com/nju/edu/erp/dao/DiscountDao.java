package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.DiscountPO;
import com.nju.edu.erp.model.vo.discount.bagVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Mapper
public interface DiscountDao {
    //获取最近一次的折扣id
    Integer getLatest();
    //创建一个折扣
    void createDiscount(DiscountPO discountPO);
    //删除一个折扣
    void deleteDiscount(Integer id);
    //获得所有折扣信息
    List<DiscountPO> getAll();

    //获取某一个等级的折扣情况
    DiscountPO getByLevel(Integer level);
    //获取高/低于某个等级的折扣情况
    List<DiscountPO> getLSTL(Integer level);
    List<DiscountPO> getBGTL(Integer level);
    //按等级更新
    void updateByLevel(DiscountPO discountPO);

    //获取所有策略二的id
    List<Integer> getIdOfSt2();
    //找到某策略下所有商品id
    List<String> getBagContent(Integer id);
    //按id更新
    void updateById(DiscountPO discountPO);
    //添加bag内容
    void addBagContent(Integer id,String pid);

    DiscountPO getById(Integer id);

    //获取某一个价位的折扣情况
    DiscountPO getByAmount(BigDecimal amount);
    //获取高/低于某个价位的折扣情况
    List<DiscountPO> getLSTA(BigDecimal amount);
    List<DiscountPO> getBGTA(BigDecimal amount);

    DiscountPO getMax(BigDecimal amount);
    //按价位更新
    void updateByAmount(DiscountPO discountPO);



}
