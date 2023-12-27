<template>
  <Layout>
    <Title title="员工打卡管理"></Title>
    已打卡人员：
    <div>
      <span><strong>请选择时间: </strong></span>
      <el-date-picker
        v-model="date"
        value-format="yyyy-MM-dd"
        type="date"
        placeholder="日期"
        @change="getchecked()">
      </el-date-picker>
    </div>
    <div style="margin-top: 10px" class = "body">
      <el-table
        :data="CheckList"
        stripe
        style="width: 100%"
        :header-cell-style="{'text-align':'center'}"
        :cell-style="{'text-align':'center'}">
        <el-table-column
          prop="name"
          label="姓名"
          width="350">
        </el-table-column>
        <el-table-column
          prop="role"
          label="职务"
          width="350">
        </el-table-column>
        <el-table-column
          prop="checktime"
          label="日期"
          width="350">
        </el-table-column>
      </el-table>
    </div>
    未打卡人员：
    <div>
      <span><strong>请选择时间: </strong></span>
      <el-date-picker
        v-model="date1"
        value-format="yyyy-MM-dd"
        type="date"
        placeholder="日期"
        @change="getunchecked()">
      </el-date-picker>
    </div>
    <div style="margin-top: 10px" class = "body">
      <el-table
        :data="UnCheckList"
        stripe
        style="width: 100%"
        :header-cell-style="{'text-align':'center'}"
        :cell-style="{'text-align':'center'}">
        <el-table-column
          prop="name"
          label="姓名"
          width="500">
        </el-table-column>
        <el-table-column
          prop="role"
          label="职务"
          width="500">
        </el-table-column>
      </el-table>
    </div>
    统计打卡次数
    <div>
      <span><strong>请选择时间段: </strong></span>
      <el-date-picker
        v-model="beginDate"
        value-format="yyyy-MM-dd"
        type="date"
        placeholder="开始日期">
      </el-date-picker>
      <el-date-picker
        v-model="endDate"
        value-format="yyyy-MM-dd"
        type="date"
        placeholder="结束日期">
      </el-date-picker>
      <el-button type="primary" size="middle" @click="getdateCheck()">查询</el-button>
    </div>
    <div style="margin-top: 10px" class = "body">
      <el-table
        :data="DateRangeCheckList"
        stripe
        style="width: 100%"
        :header-cell-style="{'text-align':'center'}"
        :cell-style="{'text-align':'center'}">
        <el-table-column
          prop="name"
          label="姓名"
          width="350">
        </el-table-column>
        <el-table-column
          prop="role"
          label="职务"
          width="350">
        </el-table-column>
        <el-table-column
          prop="times"
          label="打卡次数"
          width="350">
        </el-table-column>
      </el-table>
    </div>
  </Layout>
</template>

<script>
  import Layout from "@/components/content/Layout";
  import Title from "@/components/content/Title";
  import {getdateChecks,getdateUncheck,getdaterangeCheck} from '../../network/humanresource';

  export default {
    components: {
        Layout,
        Title,

    },
    data(){
      return {
        date:'',
        date1:'',
        beginDate:'',
        endDate:'',
        CheckList:[],  
        UnCheckList:[],
        DateRangeCheckList:[],
      }
    },
  methods: {
    getchecked(){

      const config = {
        params: {
          checktime : this.date
        }
      }
      this.CheckList = []
      getdateChecks(config).then(_res => {
      this.CheckList = this.CheckList.concat(_res.result)
    })
    },

    getunchecked(){
      const config = {
        params: {
          checktime : this.date1
        }
      }
       this.UnCheckList = []
       getdateUncheck(config).then(_res =>{
        this.UnCheckList = this.UnCheckList.concat(_res.result)
       })
    },

    getdateCheck(){
      const config = {
        params: {
          beginDate: this.beginDate,
          endDate: this.endDate
        }
      }
        this.DateRangeCheckList=[];
        getdaterangeCheck(config).then(_res =>{
          this.DateRangeCheckList = this.DateRangeCheckList.concat(_res.result)
        })
    }
  },
  };
</script>

<style scoped>
</style>