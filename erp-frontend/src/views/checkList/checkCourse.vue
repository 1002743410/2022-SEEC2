<template>
  <Layout>
    <Title title="经营历程表"></Title>
    查看经营历程表
    <el-button @click="exportAsExcel">导出Excel</el-button>
    <div>
      <span><strong>请进行条件选择: </strong></span>
      <el-date-picker
          v-model="date"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="getData1()">
      </el-date-picker>
      <el-select v-model="strategy">
        <el-option value="a">类型</el-option>
        <el-option value="b">客户</el-option>
        <el-option value="c">操作员</el-option>
      </el-select>

      <input v-model="message" >
      <el-button @click="getData2">查询</el-button>
      <div v-if="date!==''||(strategy!==''&&message!=='')" class="mt15">
        <div class="mt15">
          <span><strong>销售明细</strong></span>
          <el-table
              :data="sheetContent"
              stripe
              style="width: 100%"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"
              class="mt15">
            <el-table-column
                prop="id"
                label="id"
                width="200">
            </el-table-column>
            <el-table-column
                prop="operator"
                label="操作员"
                width="150">
            </el-table-column>
            <el-table-column
                prop="supplier"
                label="客户"
                width="100">
            </el-table-column>
            <el-table-column
                prop="saleSheetId"
                label="退货对应销售单"
                width="100">
            </el-table-column>
            <el-table-column
                prop="purchaseSheetId"
                label="退货对应进货单"
                width="100">
            </el-table-column>
            <el-table-column
                prop="remark"
                label="标注"
                width="100">
            </el-table-column>
            <el-table-column
                prop="state"
                label="单据状态"
                width="100">
            </el-table-column>
            <el-table-column
                prop="rawTotalAmount"
                label="原价(元)"
                width="100">
            </el-table-column>
            <el-table-column
                prop="finalAmount"
                label="折后价(元)"
                width="100">
            </el-table-column>
            <el-table-column
                prop="discount"
                label="折让"
                width="100">
            </el-table-column>
            <el-table-column
                prop="voucherAmount"
                label="代金券(元)"
                width="100">
            </el-table-column>
            <el-table-column
                prop="totalAmount"
                label="总价(元)"
                width="150">
            </el-table-column>

            <el-table-column
                label="时间">
              <template slot-scope="scope">
                {{formatDate(scope.row.createTime)}}
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import { formatDate } from "@/common/utils";
import {getCCByMessage, getCCByTime} from "@/network/checklist";

export default {
  components: {
    Layout,
    Title
  },
  data(){
    return{
      x:'请输入筛选条件',
      date:'',
      strategy:'',
      message:'',
      sheetContent:[],
    }
  },
  computed: {
    beginDate: function(){
      return this.date === '' ? '' : formatDate(this.date[0])
    },
    endDate: function() {
      return this.date === '' ? '' : formatDate(this.date[1])
    }
  },
  methods:{
    getData1(){
      const config = {
        params:{
          beginDateStr: this.beginDate,
          endDateStr: this.endDate
        }
      }
      getCCByTime(config).then(_res=>{
        this.sheetContent = _res.result
        console.log(this.sheetContent)
        if (this.sheetContent.length === 0) {
          this.$message.error('该时间段内无销售记录!')
        } else {
          this.$message.success('查询成功!')
        }
      })
    },

    getData2(){
      const config={
        params: {
          strategy:this.strategy,
          message:this.message
        }
      }
      getCCByMessage(config).then(_res=>{
        this.sheetContent = _res.result
        console.log(this.sheetContent)
        if (this.sheetContent.length === 0) {
          this.$message.error('该条件下无出销售记录!')
        } else {
          this.$message.success('查询成功!')
        }
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => v[j]))
    },

    exportAsExcel() {
      //TODO 导出Excel(done)
      require.ensure([], () => {
        const { export_json_to_excel } = require('@/vendor/Export2Excel');
        const tHeader=['id','操作员','客户','退货对应销售单','退货对应进货单','标注','单据状态','原价','折后价','折让','代金券','总价','时间'];
        const filterVal=['id', 'operator', 'supplier','saleSheetId','purchaseSheetId','remark','state',
        'rawTotalAmount','finalAmount','discount','voucherAmount','totalAmount','createTime'];
        const list = this.sheetContent
        const data = this.formatJson(filterVal, list);
        export_json_to_excel(tHeader, data, '列表excel');
      })
      alert("导出成Excel");
    },

    formatDate
  }
};
</script>

<style scoped>
</style>