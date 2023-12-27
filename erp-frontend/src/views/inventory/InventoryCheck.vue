<template>
  <Layout>
    <Title title="库存盘点"></Title>
    （盘点的是当天的库存快照，包括当天的各种商品的名称，型号，库存数量，库存均价，批次，批号，出厂日期，并且显示行号。要求可以导出Excel。
    <el-button @click="exportAsExcel">导出Excel</el-button>
    <div class="table-body">
      <el-table
        :data="cur_list"
        style="width: 100%;"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
      >
        <el-table-column fixed type="index" label="行号" min-width="12%">
        </el-table-column>
        <el-table-column label="商品Id" min-width="13%">
          <template slot-scope="scope">
            <el-button type="text" @click="showProduct(scope.row)">{{ scope.row.product.id }}</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="quantity" label="数量" min-width="12%">
        </el-table-column>
        <el-table-column prop="batchId" label="批次" min-width="12%">
        </el-table-column>
        <el-table-column prop="purchasePrice" label="价格" min-width="12%">
        </el-table-column>
        <el-table-column prop="productionDate" label="日期" min-width="12%">
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
      title="商品详细信息"
      :visible.sync="dialogVisible"
      width="80%"
      :before-close="handleClose">
      <div>
        <el-table
          :data="cur_product"
          style="width: 100%;"
          :header-cell-style="{'text-align': 'center'}"
          :cell-style="{'text-align': 'center'}">
          <el-table-column
            fixed
            prop="id"
            label="编号"
            min-width="15%">
          </el-table-column>
          <el-table-column
            prop="name"
            label="名称"
            min-width="10%">
          </el-table-column>
          <el-table-column
            prop="type"
            label="型号"
            min-width="15%">
          </el-table-column>
          <el-table-column
            prop="quantity"
            label="库存数量"
            min-width="8%">
          </el-table-column>
          <el-table-column
            prop="purchasePrice"
            label="进价"
            min-width="8%">
          </el-table-column>
          <el-table-column
            prop="retailPrice"
            label="零售价"
            min-width="8%">
          </el-table-column>
          <el-table-column
            prop="recentPp"
            label="最近进价"
            min-width="8%">
          </el-table-column>
          <el-table-column
            prop="recentRp"
            label="最近零售价"
            min-width="8%">
          </el-table-column>
        </el-table>
      </div>

    </el-dialog>
  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import { getWarehouseCounting } from "@/network/warehouse";

export default {
  components: {
    Layout,
    Title
  },
  data(){
    return {
      cur_list: [],
      cur_row: {},
      dialogVisible: false,
      cur_list2:[]
    }
  },
  computed: {
    cur_product() {
      let temp = [];
      temp.push(this.cur_row.product);
      return temp;
    }
  },
  mounted() {
    getWarehouseCounting().then(_res => {
      console.log("库存盘点", _res);
      this.cur_list = _res.result;
      this.cur_row = this.cur_list[0];
      for (let i in this.cur_list){
        let tem = {};
        tem.id = this.cur_list[i].id;
        tem.product = this.cur_list[i].product.id;
        tem.quantity = this.cur_list[i].quantity;
        tem.purchasePrice = this.cur_list[i].purchasePrice;
        tem.batchId = this.cur_list[i].batchId;
        tem.productionDate = this.cur_list[i].productionDate;
        this.cur_list2.push(tem)
      }
      console.log(this.cur_list2)
    })

  },
  methods: {
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => v[j]))
    },

    exportAsExcel() {
      //TODO 导出Excel(done)
      require.ensure([], () => {
        const { export_json_to_excel } = require('@/vendor/Export2Excel');
        const tHeader = ['id', '产品编号', '数量','购买价格','单号','生产日期'];
        const filterVal = ['id', 'product', 'quantity','purchasePrice','batchId','productionDate'];
        const list = this.cur_list2;
        const data = this.formatJson(filterVal, list);
        export_json_to_excel(tHeader, data, '列表excel');
      })
      alert("导出成Excel");
    },
    showProduct(row) {
      this.cur_row = row;
      this.dialogVisible = true;
    },
    handleClose(done) {
      done();
    }
  }
};
</script>

<style scoped>

.table-body {
  width: 80%;
  margin: 20px auto;
}
</style>
