package com.nju.edu.erp.service;

import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.humanresource.*;

import java.util.List;

public interface HumanresourceService {
    public void createCheck(CheckVO checkVO);
    public List<CheckPO> showDateChecks(String checktime);
    public List<UncheckPO> showDateUncheck(String checktime);
    public List<DateRangeCheckPO> showDateRangeCheck(String beginDate,String endDate);
    public void createWorker(WorkerVO workerVO);
    public List<WorkerPO> getAllWorkers();
    public void createPostInformance(PostInformanceVO postInformanceVO);
    public List<PostInformancePO> getAllPosts();
    public List<SalaryStrategyVO> getSalaryStrategy(String role);
    public void createSalaryList(SalaryListVO salaryListVO);
    public SalaryListVO getSalaryList(String name,String role);

    public List<SalaryListPO> getSalaryListByState(String state);
    public void updateSalaryListState(String name,String role,String year,String month,String state);
}
