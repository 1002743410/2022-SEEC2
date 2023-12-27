package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.BankAccountDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.BankAccountPO;
import com.nju.edu.erp.model.vo.finance.BankAccountVO;
import com.nju.edu.erp.service.BankAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    BankAccountDao bankAccountDao;

    @Autowired
    public BankAccountServiceImpl(BankAccountDao bankAccountDao){
        this.bankAccountDao = bankAccountDao;
    }
    /**
     * 增加新账户
     */
    @Override
    @Transactional
    public BankAccountVO createAccount(Integer bankAccountId, BigDecimal amount){
        BankAccountPO bankAccountPO = new BankAccountPO();
        bankAccountPO.setId(bankAccountId);
        bankAccountPO.setBankAmount(amount);
        int ans = bankAccountDao.createBankAccount(bankAccountPO);
        if (ans == 0) {
            throw new MyServiceException("A0002", "插入失败!");
        }
        BankAccountVO res = new BankAccountVO();
        BeanUtils.copyProperties(bankAccountPO,res);
        return res;
    }
    /**
     * 根据id删除指定账户
     * @param id
     */
    @Override
    public void removeAccount(Integer id){
        BankAccountPO bPO = bankAccountDao.findByBankAccountId(id);
        if(bPO == null) {
            throw new MyServiceException("A0007","需要删除的账户不存在");
        }
        else{
            bankAccountDao.deleteByBankAccountId(id);
        }

    }
    /**
     * 修改指定账户
     * @param id
     * @param amount
     */
    @Override
    public BankAccountVO updateAccount(Integer id , BigDecimal amount){
        BankAccountPO bPO = bankAccountDao.findByBankAccountId(id);
        bPO.setBankAmount(amount);
        int ans = bankAccountDao.updateByBankAccountId(bPO);
        if(ans == 0){
            throw new MyServiceException("A0003", "插入失败!");
        }
        BankAccountPO queryAns = bankAccountDao.findByBankAccountId(id);
        BankAccountVO res = new BankAccountVO();
        BeanUtils.copyProperties(queryAns,res);
        return res;
    }

    /**
     *获取全部银行账户信息
     */
    @Override
    public List<BankAccountVO> findAllAccount(){
        List<BankAccountPO> poList = bankAccountDao.findAllBankAccount();
        List<BankAccountVO> voList = new ArrayList<> ();
        for(BankAccountPO po : poList){
            BankAccountVO vo = new BankAccountVO();
            BeanUtils.copyProperties(po,vo);
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 根据id获取银行账户信息
     * @param id
     */
    @Override
    public BankAccountVO findAccountById(Integer id){
        BankAccountPO bPO = bankAccountDao.findByBankAccountId(id);
        if(bPO == null){
            throw new MyServiceException("A0007" , "未找到该账户");
        }
        BankAccountVO bVO = new BankAccountVO();
        BeanUtils.copyProperties(bPO,bVO);
        return bVO;
    }

    @Override
    public void decrease(Integer id, BigDecimal amount) {
        bankAccountDao.decrease(id, amount);
    }

}
