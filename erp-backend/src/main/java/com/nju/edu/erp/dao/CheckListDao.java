package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.CheckSaleDetailPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface CheckListDao {
    List<CheckSaleDetailPO> findCSDByTime(String beginTime,String endTime);
    List<CheckSaleDetailPO> findCSDByName(String name);
    List<CheckSaleDetailPO> findCSDByProduct(String product);
    List<CheckSaleDetailPO> findCSDByCustomer(Integer id);

    List<CheckSaleDetailPO> findCSRDByTime(String beginTime,String endTime);
    List<CheckSaleDetailPO> findCSRDByName(String name);
    List<CheckSaleDetailPO> findCSRDByProduct(String product);
    List<CheckSaleDetailPO> findCSRDByCustomer(Integer id);




}
