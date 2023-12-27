package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.finance.BankAccountVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface BankAccountService {
    /**
     * 增加新账户
     */
    BankAccountVO createAccount(Integer bankAccountId, BigDecimal amount);
    /**
     * 根据id删除指定账户
     * @param id
     */
    void removeAccount(Integer id);
    /**
     * 修改指定账户
     * @param id
     * @param amount
     */
    BankAccountVO updateAccount(Integer id , BigDecimal amount);

    /**
     *获取全部银行账户信息
     */
    List<BankAccountVO> findAllAccount();

    /**
     * 根据id获取银行账户信息
     * @param id
     */
    BankAccountVO findAccountById(Integer id);

    void decrease(Integer id,BigDecimal amount);
}
