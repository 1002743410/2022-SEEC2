package com.nju.edu.erp.web.controller;
import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.humanresource.CheckVO;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.model.vo.humanresource.PostInformanceVO;
import com.nju.edu.erp.model.vo.humanresource.SalaryListVO;
import com.nju.edu.erp.model.vo.humanresource.WorkerVO;
import com.nju.edu.erp.service.HumanresourceService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(path = "/humanresource")
public class HumanresourceController {
    private final HumanresourceService humanresourceService;
    @Autowired
    public HumanresourceController(HumanresourceService humanresourceService){
        this.humanresourceService = humanresourceService;
    }
    @PostMapping("/create")
    public Response createCheck(@RequestBody CheckVO checkVO){
        humanresourceService.createCheck(checkVO);
        return Response.buildSuccess();
    }

    @GetMapping("/showdatechecks")
    public Response showDateChecks(@RequestParam String checktime){
        return Response.buildSuccess(humanresourceService.showDateChecks(checktime));
    }
    @GetMapping("/showdateuncheck")
    public Response showDateUncheck(@RequestParam String checktime){
        return Response.buildSuccess(humanresourceService.showDateUncheck(checktime));
    }
    @GetMapping("/showdaterangecheck")
    public Response showDateRangeCheck(@RequestParam String beginDate,String endDate){
        return Response.buildSuccess(humanresourceService.showDateRangeCheck(beginDate,endDate));
    }
    @PostMapping("/createworker")
    public Response createWorker(@RequestBody WorkerVO workerVO){
        humanresourceService.createWorker(workerVO);
        return Response.buildSuccess();
    }
    @GetMapping ("/getallworkers")
    public Response getAllWorkers(){
        return Response.buildSuccess(humanresourceService.getAllWorkers());
    }
    @PostMapping("/createpostinformance")
    public Response createPostInformance(@RequestBody PostInformanceVO postInformanceVO){
        humanresourceService.createPostInformance(postInformanceVO);
        return Response.buildSuccess();
    }
    @GetMapping("/getallposts")
    public Response getAllPosts(){
        return Response.buildSuccess(humanresourceService.getAllPosts());
    }

    @GetMapping("/getsalarystrategy")
    public Response getSalaryStrategy(@RequestParam String role){
        return Response.buildSuccess(humanresourceService.getSalaryStrategy(role));
    }
    @PostMapping("/createsalarylist")
    public Response createSalaryList(@RequestBody SalaryListVO salaryListVO){
        humanresourceService.createSalaryList(salaryListVO);
        return Response.buildSuccess();
    }
    @GetMapping("/getsalarylist")
    public Response getSalaryList(@RequestParam String name,String role){
        return Response.buildSuccess(humanresourceService.getSalaryList(name,role));
    }
    @GetMapping("/choose-state")
    public Response getSalaryListByState(@RequestParam String state){
        return Response.buildSuccess(humanresourceService.getSalaryListByState(state));
    }
    @GetMapping("/update-state")
    public Response updateSalaryListState(@RequestParam String name,String role,String year,String month,String state){
        humanresourceService.updateSalaryListState(name, role, year, month, state);
        return Response.buildSuccess();
    }
}
