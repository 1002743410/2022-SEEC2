<template>
<Layout>
  <Title title="财务单据制定"></Title>
  <el-button type="primary" size="medium" @click="incomeSheet = true">制定收款单</el-button>
  <el-button type="primary" size="medium" @click="outcomeSheet = true">制定付款单</el-button>
  <el-dialog
      title="制定收款单"
      :visible.sync="incomeSheet"
      width="40%"
      :before-close="handleClose">
    <div style="width: 90%; margin: 0 auto">
      <el-form :model="incomeForm" label-width="100px" ref="incomeForm" >
        <el-form-item label="客户: " prop="custom">
          <el-select v-model="incomeForm.custom" placeholder="请选择客户">
            <el-option
                v-for="item in sellers"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
            v-for="(item, index) in incomeForm.transferAccountVOS"
            :key="index"
            :label="'转账' + index">
          <div>
            <el-select v-model="item.bid" placeholder="请选择银行账户">
              <el-option
                  v-for="item in banks"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
            <el-input v-model="item.amount" style="width: 25%; margin-right: 5%" placeholder="请输入金额"></el-input>
            <el-input v-model="item.remark" style="width: 25%;" placeholder="请输入备注"></el-input>
          </div>
          <div style="margin-top: 10px">
            <el-button type="text" size="small" @click="addTrans" v-if="index === incomeForm.transferAccountVOS.length - 1">添加</el-button>
            <el-button type="text" size="small" @click.prevent="removeTrans(item)" v-if="index !== 0">删除</el-button>
          </div>
        </el-form-item>
        <el-form-item label="备注: ">
          <el-input type="textarea" v-model="incomeForm.remark"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitIncome">立即创建</el-button>
      </span>
  </el-dialog>
  <el-dialog
      title="制定付款单"
      :visible.sync="outcomeSheet"
      width="40%"
      :before-close="handleClose">
    <div style="width: 90%; margin: 0 auto">
      <el-form :model="outcomeForm" label-width="100px" ref="outcomeForm" >
        <el-form-item label="客户: " prop="custom">
          <el-select v-model="outcomeForm.custom" placeholder="请选择客户">
            <el-option
                v-for="item in sellers"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
          <el-select v-model="outcomeForm.bid" placeholder="请选择账户">
            <el-option
                v-for="item in banks"
                :key="item"
                :label="item"
                :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
            v-for="(item, index) in outcomeForm.termSheetVOS"
            :key="index"
            :label="'条目' + index">
          <div>
            <el-input v-model="item.amount" style="width: 25%; margin-right: 5%" placeholder="请输入金额"></el-input>
            <el-input v-model="item.remark" style="width: 25%;" placeholder="请输入备注"></el-input>
          </div>
          <div style="margin-top: 10px">
            <el-button type="text" size="small" @click="addTerm" v-if="index === outcomeForm.termSheetVOS.length - 1">添加</el-button>
            <el-button type="text" size="small" @click.prevent="removeTerm(item)" v-if="index !== 0">删除</el-button>
          </div>
        </el-form-item>
        <el-form-item label="备注: ">
          <el-input type="textarea" v-model="outcomeForm.remark"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitOutcome">立即创建</el-button>
      </span>
  </el-dialog>

  <div class="body">
    <el-tabs v-model="listType" :stretch="true">
      <el-tab-pane label="收款单" name="rList">
        <div >
          <el-tabs v-model="activeName" :stretch="true">
            <el-tab-pane label="待审批" name="PENDING">
              <div v-if="pendingList.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <receipt-list :list="pendingList" :type="2" @refresh="getReceipts"/>
              </div>
            </el-tab-pane>
            <el-tab-pane label="审批完成" name="SUCCESS">
              <div v-if="successList.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <receipt-list :list="successList" :type="3"/>
              </div>
            </el-tab-pane>
            <el-tab-pane label="审批失败" name="FAILURE">
              <div v-if="failureList.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <receipt-list :list="failureList" :type="4"/>
              </div>
            </el-tab-pane>
            <el-tab-pane label="操作完成" name="DONE">
              <div v-if="doneList.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <receipt-list :list="doneList" :type="5"/>
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-tab-pane>
      <el-tab-pane label="付款单" name="paList">
        <div >
          <el-tabs v-model="activeName" :stretch="true">
            <el-tab-pane label="待审批" name="PENDING">
              <div v-if="pendingList1.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <pay-list :list="pendingList1" :type="2" @refresh="getPays"/>
              </div>
            </el-tab-pane>
            <el-tab-pane label="审批完成" name="SUCCESS">
              <div v-if="successList1.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <pay-list :list="successList1" :type="3"/>
              </div>
            </el-tab-pane>
            <el-tab-pane label="审批失败" name="FAILURE">
              <div v-if="failureList1.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <pay-list :list="failureList1" :type="4"/>
              </div>
            </el-tab-pane>
            <el-tab-pane label="处理完成" name="DONE">
              <div v-if="doneList1.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <pay-list :list="doneList1" :type="5"/>
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-tab-pane>
      <el-tab-pane label="工资单" name="saList">
        <el-select v-model="salaryBid" placeholder="请选择发放工资的银行账户">
          <el-option
              v-for="item in banks"
              :key="item"
              :label="item"
              :value="item">
          </el-option>
        </el-select>
        <el-table
            :data="salaryList"
            stripe
            style="width: 100%"
            :header-cell-style="{'text-align':'center'}"
            :cell-style="{'text-align':'center'}"
            class="mt15">
          <el-table-column
              prop="name"
              label="姓名"
              width="100">
          </el-table-column>
          <el-table-column
              prop="role"
              label="职位"
              width="100">
          </el-table-column>
          <el-table-column
              prop="basicsalary"
              label="基础工资"
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
              width="100">
          </el-table-column>
          <el-table-column
              prop="tax"
              label="税率"
              width="100">
          </el-table-column>
          <el-table-column
              prop="realsalary"
              label="实际工资"
              width="100">
          </el-table-column>
          <el-table-column
              prop="year"
              label="年"
              width="100">
          </el-table-column>
          <el-table-column
              prop="month"
              label="月"
              width="100">
          </el-table-column>
          <el-table-column
              label="发放">
            <template slot-scope="scope">
              <el-button
                  @click.native.prevent="giveSalary(scope.row)"
                  type="text"
                  size="small">
                批准
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>

  </div>



</Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import {getAllCustomer} from "@/network/purchase";
import {receiptMake, receiptShow, payMake, payShow, findBank, decreaseBank} from "@/network/finance";
import ReceiptList from "@/views/finance/components/ReceiptList";
import PayList from "@/views/finance/components/PayList";
import {chooseSalaryList, updateSalaryListState} from "@/network/humanresource";

export default {
  name: "financeSheet",
  components: {
    ReceiptList,
    Title,
    Layout,
    PayList
  },
  mounted() {
    getAllCustomer({ params : { type: 'SELLER' } }).then(_res => {
      this.sellers=_res.result
    })
    getAllCustomer({params:{type: 'SUPPLIER'}}).then(_res=>{
      for(let i in _res.result){
        this.sellers.push(_res.result[i])
      }
    })
    receiptShow({params:{state:'PENDING'}}).then(_res=>{
      this.pendingList=_res.result
      console.log(this.pendingList)
    })
    receiptShow({params:{state:'SUCCESS'}}).then(_res=>{
      this.successList=_res.result
    })
    receiptShow({params:{state:'FAILURE'}}).then(_res=>{
      this.failureList=_res.result
    })
    receiptShow({params:{state:'DONE'}}).then(_res=>{
      this.doneList=_res.result
    })
    payShow({params:{state:'PENDING'}}).then(_res=>{
      this.pendingList1=_res.result
      console.log(this.pendingList)
    })
    payShow({params:{state:'SUCCESS'}}).then(_res=>{
      this.successList1=_res.result
    })
    payShow({params:{state:'FAILURE'}}).then(_res=>{
      this.failureList1=_res.result

    })
    payShow({params:{state:'DONE'}}).then(_res=>{
      this.doneList1=_res.result
      console.log(4)
      console.log(this.doneList1)
    })
    findBank({}).then(_res=>{
      for(let i in _res.result){
        this.banks.push(_res.result[i].id)
      }
    })
    this.choosesalaryList()
  },
  data(){
    return{
      incomeSheet:false,
      outcomeSheet:false,
      salarySheet:false,
      pendingList:[],
      successList:[],
      failureList:[],
      doneList:[],
      pendingList1:[],
      successList1:[],
      failureList1:[],
      doneList1:[],
      outcomeForm:{
        termSheetVOS:[{
          id:'',
          amount:'',
          remark:'',
        }]
      },
      incomeForm:{
        transferAccountVOS:[{
          bid:'',
          amount:'',
          remark:'',
        }]
      },
      sellers:[],
      receiptList:[],
      payList:[],
      activeName:'PENDING',
      listType:"rList",
      banks:[],
      salaryList:[],
      salaryBid:''
    }
  },
  methods:{
    choosesalaryList(){
      chooseSalaryList({params:{state:'审核成功'}}).then(_res=>{
        this.salaryList = _res.result
      })
    },
    getReceipts(){
      receiptShow({params:{state:'PENDING'}}).then(_res=>{
        this.pendingList=_res.result
        console.log(this.pendingList)
      })
      receiptShow({params:{state:'SUCCESS'}}).then(_res=>{
        this.successList=_res.result
      })
      receiptShow({params:{state:'FAILURE'}}).then(_res=>{
        this.failureList=_res.result
      })
      receiptShow({params:{state:'DONE'}}).then(_res=>{
        this.doneList=_res.result
      })
    },
    getPays(){
      payShow({params:{state:'PENDING'}}).then(_res=>{
        this.pendingList1=_res.result
        console.log(this.pendingList)
      })
      payShow({params:{state:'SUCCESS'}}).then(_res=>{
        this.successList1=_res.result
      })
      payShow({params:{state:'FAILURE'}}).then(_res=>{
        this.failureList1=_res.result
      })
      payShow({params:{state:'DONE'}}).then(_res=>{
        console.log(_res.result)
        this.doneList1=_res.result
      })
    },
    handleClose(done){
      this.$confirm('确认关闭？')
          .then(_ => {
            this.resetForm()
            done();
          })
          .catch(_ => {});
    },
    resetForm(){
      this.incomeForm={
        transferAccountVOS:[{
          bid:'',
          amount:'',
          remark:'',
        }]
      }
      this.outcomeForm={
        termSheetVOS:[{
          id:'',
          amount:'',
          remark:'',
        }]
      }
    },
    addTrans(){
      this.incomeForm.transferAccountVOS.push({});
    },
    removeTrans(item){
      let index = this.incomeForm.transferAccountVOS.indexOf(item)
      if(index!==-1){
        this.incomeForm.transferAccountVOS.splice(index,1)
      }
    },
    submitIncome(){
      console.log(this.incomeForm)
      this.incomeForm.id = null
      this.incomeForm.operator = sessionStorage.getItem("name")
      receiptMake(this.incomeForm).then(_res=>{
        console.log(_res)
        if (_res.msg === 'Success') {
          this.$message.success('创建成功!')
          this.resetForm()
          this.dialogVisible = false
        }
      })
    },
    addTerm(){
      this.outcomeForm.termSheetVOS.push({})
    },
    removeTerm(item){
      let index = this.outcomeForm.termSheetVOS.indexOf(item)
      if(index!==-1){
        this.outcomeForm.termSheetVOS.splice(index,1)
      }
    },
    submitOutcome(){
      console.log(this.outcomeForm)
      this.outcomeForm.id = null
      this.outcomeForm.operator = sessionStorage.getItem("name")
      payMake(this.outcomeForm).then(_res=>{
        console.log(_res)
        if (_res.msg === 'Success') {
          this.$message.success('创建成功!')
          this.resetForm()
          this.dialogVisible = false
        }
      })
    },
    giveSalary(row){
      if(row.month===''){
        row.month='12'
      }
      let config = {
        params: {
          name:row.name,
          role:row.role,
          year:row.year,
          month:row.month,
          state:'发放完成'
        }
      };
      updateSalaryListState(config).then(_res=>{
        console.log(_res)
      })
      if(this.salaryBid===''){
        this.salaryBid=1
      }
      let c2={
        params: {
          id:this.salaryBid,
          amount:row.realsalary,
        }
      };
      decreaseBank(c2).then(_res=>{
        console.log(_res)
      })
    },
  }
}
</script>

<style scoped>

</style>