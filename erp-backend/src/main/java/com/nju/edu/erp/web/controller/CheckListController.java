package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.CheckSaleDetailPO;
import com.nju.edu.erp.model.po.OpConditionPO;
import com.nju.edu.erp.model.po.Sheets;
import com.nju.edu.erp.service.CheckListService;
import com.nju.edu.erp.web.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/checkList")
public class CheckListController {
    public CheckListService checkListService;

    @Autowired
    public CheckListController(CheckListService checkListService){
        this.checkListService = checkListService;
    }

    @PostMapping(path = "/stub1")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response stub1(){
        return Response.buildSuccess(checkListService.stub1());
    }

    @GetMapping("/saleDetail/time")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response getSaleDetailByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) throws ParseException{
        List<CheckSaleDetailPO> ans = checkListService.getCSDByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(ans);
    }
    @GetMapping("/saleDetail/message")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response getSaleDetailByMessage(@RequestParam String strategy,@RequestParam String message)throws ParseException{
        List<CheckSaleDetailPO> ans = null;
        switch (strategy){
            case "a":
                ans=checkListService.getCSDByProduct(message);
                break;
            case "b":
                ans=checkListService.getCSDByName(message);
                break;
            case "c":
                ans=checkListService.getCSDByCustomer(Integer.parseInt(message));
        }
        return Response.buildSuccess(ans);
    }

    @GetMapping("/course/time")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response getCourseByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) throws ParseException{
        List<Sheets> ans = checkListService.getSheetByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(ans);
    }

    @GetMapping("/course/message")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response getCourseByMessage(@RequestParam String strategy,@RequestParam String message)throws ParseException {
        List<Sheets> ans = null;
        switch (strategy) {
            case "a":
                ans = checkListService.getSheetByClass(message);
                break;
            case "b":
                ans = checkListService.getSheetByCustomer(Integer.parseInt(message));
                break;
            case "c":
                ans = checkListService.getSheetByOperator(message);
                break;
        }
        return Response.buildSuccess(ans);
    }

    @GetMapping("/condition/time")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response getSaleConditionByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) throws ParseException{
        OpConditionPO ans = checkListService.getOpCondition(beginDateStr, endDateStr);
        return Response.buildSuccess(ans);
    }

}
