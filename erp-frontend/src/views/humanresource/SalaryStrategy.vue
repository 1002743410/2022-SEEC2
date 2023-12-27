<template>
  <Layout>
    <Title title="酬薪规则制订"></Title>
    <el-button type="primary" size="medium" @click="dialogVisible = true" >制作工资单</el-button>
    <el-input v-model="workername" placeholder="输入员工姓名"></el-input>
    <el-select v-model="workerrole" placeholder="请选择员工岗位" label="岗位: " prop="workerrole">
      <el-option
          v-for="item in roleType"
          :key="item"
          :label="item"
          :value="item">
      </el-option>
    </el-select>
    <el-button type="primary" size="medium" @click="getsalaryList()" >查询工资单</el-button>
    <div style="margin-top: 10px" class = "body">
      <el-table
          :data="salaryList"
          stripe
          style="width: 100%"
          :header-cell-style="{'text-align':'center'}"
          :cell-style="{'text-align':'center'}">
        <el-table-column
            prop="name"
            label="姓名"
            width="100">
        </el-table-column>
        <el-table-column
            prop="role"
            label="岗位"
            width="100">
        </el-table-column>
        <el-table-column
            prop="basicsalary"
            label="基本工资"
            width="100">
        </el-table-column>
        <el-table-column
            prop="postsalary"
            label="岗位工资"
            width="100">
        </el-table-column>
        <el-table-column
            prop="commission"
            label="提成"
            width="100">
        </el-table-column>
        <el-table-column
            prop="bonus"
            label="奖金"
            width="100">
        </el-table-column>
        <el-table-column
            prop="uncheck"
            label="未打卡次数"
            width="150">
        </el-table-column>
        <el-table-column
            prop="tax"
            label="个人所得税"
            width="150">
        </el-table-column>
        <el-table-column
            prop="realsalary"
            label="实际工资"
            width="200">
        </el-table-column>
        <el-table-column
            prop="state"
            label="状态"
            width="200">
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
        title="新增工资单"
        :visible.sync="dialogVisible"
        width="40%"
    >
      <div style="width: 90%; margin: 0 auto">
        <el-form :model="salarylistForm" label-width="100px" ref="salarylistForm">
          <el-form-item label="姓名: " prop="name">
            <el-input v-model="salarylistForm.name"></el-input>
          </el-form-item>
          <el-form-item label="岗位: " prop="role">
            <el-select v-model="salarylistForm.role" placeholder="请选择员工岗位">
              <el-option
                  v-for="item in roleType"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="基本工资: " prop="basicsalary">
            <el-input v-model="salarylistForm.basicsalary"></el-input>
          </el-form-item>
          <el-form-item label="岗位工资: " prop="postsalary">
            <el-input v-model="salarylistForm.postsalary"></el-input>
          </el-form-item>
          <el-form-item label="提成: " prop="commission">
            <el-input v-model="salarylistForm.commission"></el-input>
          </el-form-item>
          <el-form-item label="奖金: " prop="bonus">
            <el-input v-model="salarylistForm.bonus"></el-input>
          </el-form-item>
          <el-form-item label="未打卡次数: " prop="uncheck">
            <el-input v-model="salarylistForm.uncheck"></el-input>
          </el-form-item>
          <el-form-item label="年: " prop="year">
            <el-input v-model="salarylistForm.year"></el-input>
          </el-form-item>
          <el-form-item label="月: " prop="month">
            <el-input v-model="salarylistForm.month"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm()">立即创建</el-button>
      </span>
    </el-dialog>
  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import {createSalaryList, getSalaryStrategy,getSalaryList} from '../../network/humanresource';
export default {
  components: {
    Layout,
    Title
  },
  data(){
    return{
      role:'',
      roletype:["INVENTORY_MANAGER","SALE_STAFF","SALE_MANAGER","HR","GM"],
      SalaryStrategyList:[],
      salaryList:[],
      roleType:["库存管理人员","进货销售人员","销售经理","财务人员","人力资源人员","总经理"],
      salarylistForm:{
        name:'',
        role:'',
        basicsalary:'',
        postsalary:'',
        commission:'',
        bonus:'',
        uncheck:'',
        tax:'',
        realsalary:''
      },
      dialogVisible : false,
      salary:'',
      workername:'',
      workerrole:'',
    }
  },
  methods:{
    getsalaryList(){
      const config = {
        params: {
          name:this.workername,
          role: this.workerrole
        }
      }
      getSalaryList(config).then(_res => {
        this.salaryList = this.salaryList.concat(_res.result)
      })
    },

    GetSalaryStrategy(){
      console.log("1111111111111")
      const config = {
        params: {
          role: this.role
        }
      }
      this.SalaryStrategyList=[];
      getSalaryStrategy(config).then(_res => {
        console.log(_res)
        this.SalaryStrategyList = this.SalaryStrategyList.concat(_res.result)
      })
    },
    submitForm(){
      let salary = 0
      if(this.salarylistForm.role!="总经理"){
        salary = Number(this.salarylistForm.basicsalary)*(1-Number(this.salarylistForm.uncheck)/30)
            + Number(this.salarylistForm.postsalary) + Number(this.salarylistForm.commission)+ Number(this.salarylistForm.bonus)
        if(salary<=3000){
          this.salarylistForm.tax = Number(salary * 0.03)
        }else if(3000<salary&&salary<=12000){
          this.salarylistForm.tax = Number(90 + (salary-3000)*0.1)
        }else if(12000<salary&&salary<=25000){
          this.salarylistForm.tax = Number(90+900+(salary-12000)*0.2)
        }else if(25000<salary&&salary<=35000){
          this.salarylistForm.tax = Number(90+900+2600+(salary-25000)*0.25)
        }else if(35000<salary&&salary<=55000){
          this.salarylistForm.tax = Number(90+900+2600+2500+(salary-35000)*0.3)
        }else if(55000<salary&&salary<=80000){
          this.salarylistForm.tax = Number(90+900+2600+2500+6000+(salary-55000)*0.35)
        }else if(salary>80000){
          this.salarylistForm.tax = Number(90+900+2600+2500+6000+8750+(salary-80000)*0.45)
        }
      }else{
        salary = Number(this.salarylistForm.basicsalary)+Number(this.salarylistForm.postsalary)
            + Number(this.salarylistForm.commission)+ Number(this.salarylistForm.bonus)
        if(salary<=36000){
          this.salarylistForm.tax = Number(salary * 0.03)
        }else if(36000<salary&&salary<=144000){
          this.salarylistForm.tax = Number(1080 + (salary-36000)*0.1)
        }else if(144000<salary&&salary<=300000){
          this.salarylistForm.tax = Number(1080+10800+(salary-144000)*0.2)
        }else if(300000<salary&&salary<=420000){
          this.salarylistForm.tax = Number(1080+10800+31200+(salary-300000)*0.25)
        }else if(420000<salary&&salary<=660000){
          this.salarylistForm.tax = Number(1080+10800+31200+30000+(salary-420000)*0.3)
        }else if(660000<salary&&salary<=960000){
          this.salarylistForm.tax = Number(1080+10800+31200+30000+72000+(salary-660000)*0.35)
        }else if(salary>960000){
          this.salarylistForm.tax = Number(1080+10800+31200+30000+72000+105000+(salary-960000)*0.45)
        }
      }
      this.salarylistForm.realsalary = Number(salary-this.salarylistForm.tax)
      this.salarylistForm.basicsalary =Number(this.salarylistForm.basicsalary)
      this.salarylistForm.postsalary = Number(this.salarylistForm.postsalary)
      this.salarylistForm.commission = Number(this.salarylistForm.commission)
      this.salarylistForm.bonus = Number(this.salarylistForm.bonus)
      this.salarylistForm.uncheck = Number(this.salarylistForm.uncheck)
      this.salarylistForm.tax = Number(this.salarylistForm.tax)
      this.salarylistForm.realsalary = Number(this.salarylistForm.realsalary)
      createSalaryList(this.salarylistForm).then(_res => {
        if (_res.msg === 'Success') {
          this.$message.success('创建成功!')
          this.dialogVisible = false
        }
      })
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            this.resetForm()
            done();
          })
          .catch(_ => {});
    },
  }
};
</script>

<style scoped>
</style>