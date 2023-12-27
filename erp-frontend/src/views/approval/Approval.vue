<template>
  <Layout>
    <Title title="审批"></Title>
    <div class="body">
      <el-tabs v-model="listType" :stretch="true">
        <el-tab-pane label="销售单" name="sList">
          <div >
            <el-tabs v-model="activeName" :stretch="true">
              <el-tab-pane label="待一级审批" name="PENDING_LEVEL_1">
                <div v-if="pendingLevel1List.length === 0">
                  <el-empty description="暂无数据"></el-empty>
                </div>
                <div v-else>
                  <sale-list :list="pendingLevel1List" :type="1" @refresh="getSale()"/>
                </div>
              </el-tab-pane>
              <el-tab-pane label="待二级审批" name="PENDING_LEVEL_2">
                <div v-if="pendingLevel2List.length === 0">
                  <el-empty description="暂无数据"></el-empty>
                </div>
                <div v-else>
                  <sale-list :list="pendingLevel2List" :type="2" @refresh="getSale()"/>
                </div>
              </el-tab-pane>
              <el-tab-pane label="审批完成" name="SUCCESS">
                <div v-if="successList.length === 0">
                  <el-empty description="暂无数据"></el-empty>
                </div>
                <div v-else>
                  <sale-list :list="successList" :type="3"/>
                </div>
              </el-tab-pane>
              <el-tab-pane label="审批失败" name="FAILURE">
                <div v-if="failureList.length === 0">
                  <el-empty description="暂无数据"></el-empty>
                </div>
                <div v-else>
                  <sale-list :list="failureList" :type="4"/>
                </div>
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-tab-pane>
        <el-tab-pane label="销售退货单" name="srList">
          <el-tabs v-model="activeName2" :stretch="true">
            <el-tab-pane label="待一级审批" name="PENDING_LEVEL_1">
              <div v-if="pendingLevel1List2.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <sale-return-list :list="pendingLevel1List2" :type="1" @refresh="getSaleReturn()"/>
              </div>
            </el-tab-pane>
            <el-tab-pane label="待二级审批" name="PENDING_LEVEL_2">
              <div v-if="pendingLevel2List2.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <sale-return-list :list="pendingLevel2List2" :type="2" @refresh="getSaleReturn()"/>
              </div>
            </el-tab-pane>
            <el-tab-pane label="审批完成" name="SUCCESS">
              <div v-if="successList2.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <sale-return-list :list="successList2" :type="3"/>
              </div>
            </el-tab-pane>
            <el-tab-pane label="审批失败" name="FAILURE">
              <div v-if="failureList2.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <sale-return-list :list="failureList2" :type="4"/>
              </div>
            </el-tab-pane>
          </el-tabs>
        </el-tab-pane>
        <el-tab-pane label="进货单" name="pList">
          <el-tabs v-model="activeName3" :stretch="true">
            <el-tab-pane label="待一级审批" name="PENDING_LEVEL_1">
              <div v-if="pendingLevel1List3.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <purchase-list :list="pendingLevel1List3" :type="1" @refresh="getPurchase()"/>
              </div>
            </el-tab-pane>
            <el-tab-pane label="待二级审批" name="PENDING_LEVEL_2">
              <div v-if="pendingLevel2List3.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <purchase-list :list="pendingLevel2List3" :type="2" @refresh="getPurchase()"/>
              </div>
            </el-tab-pane>
            <el-tab-pane label="审批完成" name="SUCCESS">
              <div v-if="successList3.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <purchase-list :list="successList3" :type="3"/>
              </div>
            </el-tab-pane>
            <el-tab-pane label="审批失败" name="FAILURE">
              <div v-if="failureList3.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <purchase-list :list="failureList3" :type="4"/>
              </div>
            </el-tab-pane>
          </el-tabs>
        </el-tab-pane>
        <el-tab-pane label="进货退货单" name="prList">
          <el-tabs v-model="activeName" :stretch="true">
            <el-tab-pane label="待一级审批" name="PENDING_LEVEL_1">
              <div v-if="pendingLevel1List4.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <purchase-return-list :list="pendingLevel1List4" :type="1" @refresh="getPurchaseReturn()"/>
              </div>
            </el-tab-pane>
            <el-tab-pane label="待二级审批" name="PENDING_LEVEL_2">
              <div v-if="pendingLevel2List4.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <purchase-return-list :list="pendingLevel2List4" :type="2" @refresh="getPurchaseReturn()"/>
              </div>
            </el-tab-pane>
            <el-tab-pane label="审批完成" name="SUCCESS">
              <div v-if="successList4.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <purchase-return-list :list="successList4" :type="3"/>
              </div>
            </el-tab-pane>
            <el-tab-pane label="审批失败" name="FAILURE">
              <div v-if="failureList4.length === 0">
                <el-empty description="暂无数据"></el-empty>
              </div>
              <div v-else>
                <purchase-return-list :list="failureList4" :type="4"/>
              </div>
            </el-tab-pane>
          </el-tabs>
        </el-tab-pane>
        <el-tab-pane label="收款单" name="rList">
          <div >
            <el-tabs v-model="activeName5" :stretch="true">
              <el-tab-pane label="待审批" name="PENDING">
                <div v-if="pendingList.length === 0">
                  <el-empty description="暂无数据"></el-empty>
                </div>
                <div v-else>
                  <receipt-list :list="pendingList" :type="2" @refresh="getReceipts"/>
                </div>
              </el-tab-pane>
              <el-tab-pane label="审批完成" name="SUCCESS">
                <div v-if="successList5.length === 0">
                  <el-empty description="暂无数据"></el-empty>
                </div>
                <div v-else>
                  <receipt-list :list="successList5" :type="3"/>
                </div>
              </el-tab-pane>
              <el-tab-pane label="审批失败" name="FAILURE">
                <div v-if="failureList5.length === 0">
                  <el-empty description="暂无数据"></el-empty>
                </div>
                <div v-else>
                  <receipt-list :list="failureList5" :type="4"/>
                </div>
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-tab-pane>
        <el-tab-pane label="付款单" name="paList">
          <div >
            <el-tabs v-model="activeName6" :stretch="true">
              <el-tab-pane label="待审批" name="PENDING">
                <div v-if="pendingList1.length === 0">
                  <el-empty description="暂无数据"></el-empty>
                </div>
                <div v-else>
                  <pay-list :list="pendingList1" :type="2" @refresh="getPays"/>
                </div>
              </el-tab-pane>
              <el-tab-pane label="审批完成" name="SUCCESS">
                <div v-if="successList6.length === 0">
                  <el-empty description="暂无数据"></el-empty>
                </div>
                <div v-else>
                  <pay-list :list="successList6" :type="3"/>
                </div>
              </el-tab-pane>
              <el-tab-pane label="审批失败" name="FAILURE">
                <div v-if="failureList6.length === 0">
                  <el-empty description="暂无数据"></el-empty>
                </div>
                <div v-else>
                  <pay-list :list="failureList6" :type="4"/>
                </div>
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-tab-pane>
        <el-tab-pane label="工资单" name="saList">
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
                label="批准">
              <template slot-scope="scope">
                <el-button
                    @click.native.prevent="approveSalary(scope.row)"
                    type="text"
                    size="small">
                  批准
                </el-button>
              </template>
            </el-table-column>
            <el-table-column
                label="否决">
              <template slot-scope="scope">
                <el-button
                    @click.native.prevent="disapproveSalary(scope.row)"
                    type="text"
                    size="small">
                  否决
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
  import SaleList from '../sale/components/SaleList'
  import saleReturnList from "../sale/components/SaleReturnList"
  import PurchaseList from "../purchase/components/PurchaseList"
  import purchaseReturnList from "../purchase/components/PurchaseReturnList"
  import PayList from "@/views/finance/components/PayList";
  import ReceiptList from "@/views/finance/components/ReceiptList";
  import { getAllSale,getAllSaleReturn } from '../../network/sale'
  import { getAllPurchaseReturn, getAllPurchase,} from '../../network/purchase'
  import {payShow, receiptShow} from "@/network/finance";
  import {chooseSalaryList, updateSalaryListState} from "@/network/humanresource";
  export default {
    components: {
        Layout,
        Title,
        SaleList,
        saleReturnList,
        PurchaseList,
        purchaseReturnList,
        PayList,
        ReceiptList
    },
    data(){
      return{
        listType:'sList',

        activeName: 'PENDING_LEVEL_1',
        saleList: [],
        pendingLevel1List: [],
        pendingLevel2List: [],
        successList: [],
        failureList: [],

        activeName2: 'PENDING_LEVEL_1',
        saleReturnList: [],
        pendingLevel1List2: [],
        pendingLevel2List2: [],
        successList2: [],
        failureList2: [],

        activeName3: 'PENDING_LEVEL_1',
        purchaseList: [],
        pendingLevel1List3: [],
        pendingLevel2List3: [],
        successList3: [],
        failureList3: [],

        activeName4: 'PENDING_LEVEL_1',
        purchaseReturnList: [],
        pendingLevel1List4: [],
        pendingLevel2List4: [],
        successList4: [],
        failureList4: [],

        activeName5:'PENDING',
        pendingList:[],
        successList5: [],
        failureList5: [],

        activeName6:'PENDING',
        pendingList1:[],
        successList6: [],
        failureList6: [],

        salaryList:[]
      }
    },
    mounted() {
      this.getSale()
      this.getSaleReturn()
      this.getPurchase()
      this.getPurchaseReturn()
      this.getReceipts()
      this.getPays()
      this.choosesalaryList()
    },
    methods:{
      getSale() {
        getAllSale({}).then(_res => {
          this.saleList = _res.result
          this.pendingLevel1List = this.saleList.filter(item => item.state === '待一级审批')
          this.pendingLevel2List = this.saleList.filter(item => item.state === '待二级审批')
          this.successList = this.saleList.filter(item => item.state === '审批完成')
          this.failureList = this.saleList.filter(item => item.state === '审批失败')
        })
      },
      getSaleReturn() {
        getAllSaleReturn({}).then(_res => {
          this.saleReturnList = _res.result
          this.pendingLevel1List2 = this.saleReturnList.filter(item => item.state === '待一级审批')
          this.pendingLevel2List2 = this.saleReturnList.filter(item => item.state === '待二级审批')
          this.successList2 = this.saleReturnList.filter(item => item.state === '审批完成')
          this.failureList2 = this.saleReturnList.filter(item => item.state === '审批失败')
        })
      },
      getPurchase() {
        getAllPurchase({}).then(_res => {
          this.purchaseList = _res.result
          this.pendingLevel1List3 = this.purchaseList.filter(item => item.state === '待一级审批')
          this.pendingLevel2List3 = this.purchaseList.filter(item => item.state === '待二级审批')
          this.successList3 = this.purchaseList.filter(item => item.state === '审批完成')
          this.failureList3 = this.purchaseList.filter(item => item.state === '审批失败')
        })
      },
      getPurchaseReturn() {
        getAllPurchaseReturn({}).then(_res => {
          this.purchaseReturnList = _res.result
          this.pendingLevel1List4 = this.purchaseReturnList.filter(item => item.state === '待一级审批')
          this.pendingLevel2List4 = this.purchaseReturnList.filter(item => item.state === '待二级审批')
          this.successList4 = this.purchaseReturnList.filter(item => item.state === '审批完成')
          this.failureList4 = this.purchaseReturnList.filter(item => item.state === '审批失败')
        })
      },
      getReceipts(){
        receiptShow({params:{state:'PENDING'}}).then(_res=>{
          this.pendingList=_res.result
          console.log(this.pendingList)
        })
        receiptShow({params:{state:'SUCCESS'}}).then(_res=>{
          this.successList5=_res.result
        })
        receiptShow({params:{state:'FAILURE'}}).then(_res=>{
          this.failureList5=_res.result
        })
      },
      getPays(){
        payShow({params:{state:'PENDING'}}).then(_res=>{
          this.pendingList1=_res.result
          console.log(this.pendingList)
        })
        payShow({params:{state:'SUCCESS'}}).then(_res=>{
          this.successList6=_res.result
        })
        payShow({params:{state:'FAILURE'}}).then(_res=>{
          this.failureList6=_res.result
        })
      },
      choosesalaryList(){
        chooseSalaryList({params:{state:'待审批'}}).then(_res=>{
          this.salaryList = _res.result
        })
      },
      approveSalary(row){
        if(row.month===''){
          row.month='12'
        }
        let config = {
          params: {
            name:row.name,
            role:row.role,
            year:row.year,
            month:row.month,
            state:'审核成功'
          }
        };
        updateSalaryListState(config).then(_res=>{
          console.log(_res)
        })
      },
      disapproveSalary(row){
        if(row.month===''){
          row.month='12'
        }
        let config = {
          params: {
            name:row.name,
            role:row.role,
            year:row.year,
            month:row.month,
            state:'审核失败'
          }
        };
        updateSalaryListState(config).then(_res=>{
          console.log(_res)
        })
      },
    }
  };
</script>

<style scoped>
</style>
