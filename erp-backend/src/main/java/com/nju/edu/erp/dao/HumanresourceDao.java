package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.humanresource.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
@Mapper
public interface HumanresourceDao {
    void createCheck(CheckVO checkVO);
    List<CheckPO> showDateChecks(String checktime);
    List<UncheckPO> showDateUncheck(String checktime);
    List<DateRangeCheckPO> showDateRangeCheck(String beginDate,String endDate);
    void createWorker(WorkerVO workerVO);
    List<WorkerPO> getAllWorkers();
    void createPostInformance(PostInformanceVO postInformanceVO);
    List<PostInformancePO> getAllPosts();
    void createSalaryList(SalaryListVO salaryListVO);
    SalaryListVO getSalaryList(String name,String role);
    void createSalaryStrategy(SalaryStrategyVO salaryStrategyVO);
    List<SalaryStrategyVO> getSalaryStrategy(String role);

    List<SalaryListPO> getSalaryListByState(String state);
    void updateSalaryList(String name,String role,String year,String month,String state);
}

