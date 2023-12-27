package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.BankAccountPO;
import com.nju.edu.erp.model.vo.finance.BankAccountVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Mapper
public interface BankAccountDao {
    int createBankAccount(BankAccountPO bankAccountPO);

    BankAccountPO findByBankAccountId(Integer bankAccountId);

    // 查询所有银行账户
    List<BankAccountPO> findAllBankAccount();

    // 修改银行账户
    int updateByBankAccountId(BankAccountPO bankAccountPO);

    // 删除银行账户
    int deleteByBankAccountId(Integer BankAccountId);

    BigDecimal getAmountById(Integer id);

    void decrease(Integer id,BigDecimal amount);
}
