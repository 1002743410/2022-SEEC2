<template>
<Layout>
  <Title title="账户管理"></Title>
  <el-button type="primary" size="medium" @click="dialogVisible = true">新建账户</el-button>
  <div class="mt15">
    <el-table
        :data="bankList"
        stripe
        style="width: 100%"
        :header-cell-style="{'text-align':'center'}"
        :cell-style="{'text-align':'center'}"
        class="mt15">
      <el-table-column
          prop="id"
          label="账号"
          width="500">
      </el-table-column>
      <el-table-column
          prop="bankAmount"
          label="金额"
          width="500">
      </el-table-column>
      <el-table-column
          label="操作">
        <template slot-scope="scope">
          <el-button
              @click.native.prevent="deletebank(scope.row.id)"
              type="text"
              size="small">
            删除
          </el-button>
        </template>
      </el-table-column>

    </el-table>
  </div>
  <el-dialog
      title="创建账户"
      :visible.sync="dialogVisible"
      width="40%"
      :before-close="handleClose">
    <div style="width: 90%; margin: 0 auto">
      <el-form :model="bank" label-width="100px" ref="bankList" >
        <el-form-item label="账户名: ">
          <el-input type="textarea" v-model="bank.bankAccountId"></el-input>
        </el-form-item>
        <el-form-item label="金额: ">
          <el-input type="textarea" v-model="bank.amount"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitBank">立即创建</el-button>
      </span>
  </el-dialog>
</Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import {findBank,deleteBank,createBank} from "@/network/finance";

export default {
  name: "bankManange",
  components: {
    Layout,
    Title
  },
  mounted() {
    findBank({}).then(_res=>{
      this.bankList=_res.result
    })
  },
  data(){
    return{
      dialogVisible:false,
      bank:{
        bankAccountId:'',
        amount:'',
      },
      bankList:[]
    }
  },
  methods:{
    deletebank(id){
      let config = {
        params: {
          bankAccountId: Number(id)
        }
      };
      this.$confirm('是否要删除该账户？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() =>{
        deleteBank(config).then(_res =>{
          if (_res.msg === 'Success') {
            this.$message.success('删除成功!')
          }
        })
        findBank()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    submitBank(){
      let config = {
        params: {
          bankAccountId: Number(this.bank.bankAccountId),
          amount:Number(this.bank.amount)
        }
      };

      createBank(config).then(_res=>{
        console.log(_res)
        if (_res.msg === 'Success') {
          this.$message.success('创建成功!')
          this.resetForm()
          this.dialogVisible = false
        }
      })
      findBank()
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
      this.bank={
        id:'',
        amount:'',
      }
    },
  }
}
</script>

<style scoped>

</style>