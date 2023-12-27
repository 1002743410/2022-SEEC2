package com.nju.edu.erp.web.controller;


import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.ReceiptState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.finance.ReceiptVO;
import com.nju.edu.erp.service.ReceiptService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/receipt")
public class ReceiptController {
    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService) {this.receiptService = receiptService;};

    /**
     * 财务人员制定收款单
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makeReceiptOrder(@RequestBody ReceiptVO receiptVO)  {
        receiptService.makeReceipt(receiptVO);
        return Response.buildSuccess();
    }

    /**
     * 根据状态查看收款单
     */
    @GetMapping(value = "/sheet-show")
    public Response showReceiptSheetByState (@RequestParam(value = "state", required = false)ReceiptState state) {
        return Response.buildSuccess(receiptService.getReceiptByState(state));
    }

    /**
     * 总经理审批
     * @param receiptId 收款单id
     * @param state 修改后的状态("审批失败"/"审批完成")
     */
    @Authorized (roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/second-approval")
    public Response Approval(@RequestParam("receiptId") String receiptId,
                                   @RequestParam("state") ReceiptState state)  {
        if(state.equals(ReceiptState.FAILURE) || state.equals(ReceiptState.SUCCESS)) {
            receiptService.approval(receiptId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000","操作失败");
        }
    }

    /**
     * 根据收款单Id搜索收款单信息
     * @param id 收款单Id
     * @return 收款单全部信息
     */
    @GetMapping(value = "/find-sheet")
    public Response findByReceiptId(@RequestParam(value = "id") String id)  {
        return Response.buildSuccess(receiptService.getReceiptById(id));
    }

    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @GetMapping(value = "/makeChange")
    public Response makeChange(@RequestParam("receiptId") String receiptId){
        receiptService.makeChange(receiptId);
        return Response.buildSuccess();
    }

}
