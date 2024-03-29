package com.nju.edu.erp.service.Impl;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.humanresource.*;
import com.nju.edu.erp.service.HumanresourceService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.nju.edu.erp.dao.HumanresourceDao;

import java.util.List;

@Service
public class HumanresourceServiceImpl implements HumanresourceService {
    private final HumanresourceDao humanresourceDao;
    @Autowired
    public HumanresourceServiceImpl(HumanresourceDao humanresourceDao){
        this.humanresourceDao = humanresourceDao;
    }
    public void createCheck(CheckVO checkVO){
        humanresourceDao.createCheck(checkVO);
    }
    public List<CheckPO> showDateChecks(String checktime){
        return humanresourceDao.showDateChecks(checktime);
    }
    public List<UncheckPO> showDateUncheck(String checktime){
        return humanresourceDao.showDateUncheck(checktime);
    }
    public List<DateRangeCheckPO> showDateRangeCheck(String beginDate,String endDate){
        return humanresourceDao.showDateRangeCheck(beginDate,endDate);
    }
    public void createWorker(WorkerVO workerVO){
        humanresourceDao.createWorker(workerVO);
    }
    public List<WorkerPO> getAllWorkers(){
        return humanresourceDao.getAllWorkers();
    }
    public void createPostInformance(PostInformanceVO postInformanceVO){
        humanresourceDao.createPostInformance(postInformanceVO);
    }
    public List<PostInformancePO> getAllPosts(){
        return humanresourceDao.getAllPosts();
    }
    public void createSalaryList(SalaryListVO salaryListVO){
        humanresourceDao.createSalaryList(salaryListVO);
    }
    public SalaryListVO getSalaryList(String name,String role){
        return humanresourceDao.getSalaryList(name,role);
    }

    @Override
    public List<SalaryListPO> getSalaryListByState(String state) {
        return humanresourceDao.getSalaryListByState(state);
    }

    @Override
    public void updateSalaryListState(String name, String role, String year, String month, String state) {
        humanresourceDao.updateSalaryList(name, role, year, month, state);
    }

    public List<SalaryStrategyVO> getSalaryStrategy(String role){
        return humanresourceDao.getSalaryStrategy(role);
    }
}
