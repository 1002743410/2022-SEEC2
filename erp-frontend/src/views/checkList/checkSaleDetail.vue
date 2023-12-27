<template>
  <Layout>
    <Title title="查看销售明细表"></Title>
    查看销售明细表
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
        <el-option value="a">商品名</el-option>
        <el-option value="b">业务员</el-option>
        <el-option value="c">客户</el-option>
      </el-select>
      <input v-model="message">
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
                prop="t"
                label="类型"
                width="100">
            </el-table-column>
            <el-table-column
                prop="name"
                label="产品名称"
                width="200">
            </el-table-column>
            <el-table-column
                prop="type"
                label="产品所属分类"
                width="200">
            </el-table-column>
            <el-table-column
                prop="quantity"
                label="数量"
                width="150">
            </el-table-column>
            <el-table-column
                prop="unitPrice"
                label="单价(元)"
                width="150">
            </el-table-column>
            <el-table-column
                prop="totalPrice"
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
import { getSDByTime,getSDByMessage} from "@/network/checklist";
export default {
  components: {
    Layout,
    Title
  },
  data(){
    return{
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
      getSDByTime(config).then(_res=>{
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
      getSDByMessage(config).then(_res=>{
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
        const tHeader=['类型','产品名称','产品所属分类','数量','单价','总价','创建时间'];
        const filterVal=['t','name','type','quantity','unitPrice','totalPrice','createTime'];
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
.mt15 {
margin-top: 15px;
}
<style scoped>
</style>