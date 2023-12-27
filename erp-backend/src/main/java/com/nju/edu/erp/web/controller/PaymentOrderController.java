package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.PaymentOrderState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.finance.PaymentOrderVO;
import com.nju.edu.erp.service.PaymentOrderService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/payment-order")
public class PaymentOrderController {
    private final PaymentOrderService paymentOrderService;

    @Autowired
    public PaymentOrderController(PaymentOrderService paymentOrderService) {this.paymentOrderService = paymentOrderService;}

    /**
     * 财务人员制定付款单
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response make_PaymentOrder(UserVO userVO, @RequestBody PaymentOrderVO paymentOrderVO){
        paymentOrderService.makePaymentOrder(paymentOrderVO);
        return Response.buildSuccess();
    }
    /**
     * 根据状态查看付款单
     */
    @GetMapping(value = "/sheet-show")
    public Response showPaymentOrderByState (@RequestParam(value = "state", required = false) PaymentOrderState state) {
        return Response.buildSuccess(paymentOrderService.findPaymentOrderByState(state));
    }

    /**
     * 总经理审批
     * @param paymentOrderId 付款单id
     * @param state 修改后的状态("审批失败"/"审批完成")
     */
    @Authorized (roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/second-approval")
    public Response Approval(@RequestParam("paymentOrderId") String paymentOrderId,
                             @RequestParam("state") PaymentOrderState state)  {
        if(state.equals(PaymentOrderState.FAILURE) || state.equals(PaymentOrderState.SUCCESS)) {
            paymentOrderService.approval(paymentOrderId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000","操作失败");
        }
    }

    /**
     * 根据付款单Id搜索付款单信息
     * @param id 付款单Id
     * @return 付款单全部信息
     */
    @GetMapping(value = "/find-sheet")
    public Response findByPaymentOrderId(@RequestParam(value = "id") String id)  {
        return Response.buildSuccess(paymentOrderService.findPaymentOrderById(id));
    }

    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @GetMapping(value = "/makeChange")
    public Response makeChange(@RequestParam("paymentOrderId") String paymentOrderId){
        return Response.buildSuccess(paymentOrderService.makeChange(paymentOrderId));
    }

}
