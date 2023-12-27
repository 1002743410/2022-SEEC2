<template>
  <Layout>
    <Title title="经营情况表"></Title>
    查看经营情况表
    <div>
      <el-date-picker
          v-model="date"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="getData()">
      </el-date-picker>
      <div v-if="date!==''" class="mt15">
        <div class="mt15">

          <el-table
              :data="condition"
              stripe
              style="width: 100%"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"
              class="mt15">
            <el-table-column
                prop="totalIncome"
                label="折让后总收入"
                width="300">
            </el-table-column>
            <el-table-column
                prop="discountAmount"
                label="折让"
                width="300">
            </el-table-column>
            <el-table-column
                prop="totalOutcome"
                label="总支出"
                width="300">
            </el-table-column>
            <el-table-column
                prop="profit"
                label="利润"
                width="300">
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
import {getCondition} from "@/network/checklist";
import { formatDate } from "@/common/utils";

export default {
  components: {
    Layout,
    Title
  },
  data(){
    return{
      date:'',
      condition:[]
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
    getData(){
      const config = {
        params:{
          beginDateStr: this.beginDate,
          endDateStr: this.endDate
        }
      }
      getCondition(config).then(_res=>{
        console.log(_res.result)
        this.condition.push(_res.result)
      })
    }
  }
};
</script>

<style scoped>
</style>
