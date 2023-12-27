package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.finance.BankAccountVO;
import com.nju.edu.erp.service.BankAccountService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/bank-account")
public class BankAccountController {

    private BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService){this.bankAccountService = bankAccountService;}

    @GetMapping("/create")
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    public Response createBankAccout(@RequestParam(value = "bankAccountId") Integer bankAccountId,
                                     @RequestParam(value = "amount")BigDecimal amount){
        return Response.buildSuccess(bankAccountService.createAccount(bankAccountId, amount));
    }
    @GetMapping("/findAll")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    public Response findAllBankAccount(){return  Response.buildSuccess(bankAccountService.findAllAccount());}

    @GetMapping("/update")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    public Response updateBankAccount(@RequestParam(value = "bankAccountId") Integer bankccountId,
                                      @RequestParam(value = "amount") BigDecimal amount){
        return Response.buildSuccess(bankAccountService.updateAccount(bankccountId, amount));
    }

    @GetMapping("/delete")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    public Response deleteAccount(@RequestParam(value = "bankAccountId") int bankAccountId) {
        bankAccountService.removeAccount(bankAccountId);
        return Response.buildSuccess();
    }

    @GetMapping("/decrease")
    public Response decreaseBankAmount(@RequestParam Integer id,BigDecimal amount){
        bankAccountService.decrease(id, amount);
        return Response.buildSuccess();
    }


}
