<template>
  <Layout>
    <Title title="客户管理"></Title>
    <el-button type="primary" size="medium" @click="dialogVisible = true">新增客户</el-button>
    <div style="margin-top: 10px" class = "body">
      <el-table
          :data="customerList"
          stripe
          style="width: 100%"
          :header-cell-style="{'text-align':'center'}"
          :cell-style="{'text-align':'center'}">
        <el-table-column
            prop="id"
            label="id"
            width="60">
        </el-table-column>
        <el-table-column
            prop="name"
            label="姓名"
            width="70">
        </el-table-column>
        <el-table-column
            prop="type"
            label="类型"
            width="100"
            :filters="[{ text: '供应商', value: '供应商' }, { text: '销售商', value: '销售商' }]"
            :filter-method="filterTag"
            filter-placement="bottom-end">
          <template slot-scope="scope">
            <el-tag
                :type="scope.row.type === '供应商' ? 'primary' : 'success'"
                disable-transitions>{{scope.row.type}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="level"
            label="级别"
            width="50">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="电话"
            width="150">
        </el-table-column>
        <el-table-column
            prop="address"
            label="地址"
            width="150">
        </el-table-column>
        <el-table-column
            prop="zipcode"
            label="邮编"
            width="100">
        </el-table-column>
        <el-table-column
            prop="email"
            label="邮箱"
            width="200">
        </el-table-column>
        <el-table-column
            prop="lineOfCredit"
            label="应收额度(元)"
            width="120">
        </el-table-column>
        <el-table-column
            prop="receivable"
            label="应收(元)"
            width="120">
        </el-table-column>
        <el-table-column
            prop="payable"
            label="应付(元)"
            width="120">
        </el-table-column>
        <el-table-column
            prop="operator"
            label="操作员"
            width="120">
        </el-table-column>
        <el-table-column
            label="操作">
          <template slot-scope="scope">
            <el-button
                @click.native.prevent="deletecustomer(scope.row.id)"
                type="text"
                size="small">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
        title="新增用户"
        :visible.sync="dialogVisible"
        width="40%"
        :before-close="handleClose">
      <div style="width: 90%; margin: 0 auto">
        <el-form :model="customerForm" label-width="100px" ref="customerForm" :rules="rules">

          <el-form-item label="ID: " prop="id">
            <el-input v-model="customerForm.id"></el-input>
          </el-form-item>
          <el-form-item label="姓名: " prop="name">
            <el-input v-model="customerForm.name"></el-input>
          </el-form-item>
          <el-form-item label="类型: " prop="type">
            <el-select v-model="customerForm.type" placeholder="请选择客户类型">
              <el-option
                  v-for="item in customertype"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="级别: " prop="level">
            <el-select v-model="customerForm.level" placeholder="请选择客户级别">
              <el-option
                  v-for="item in customerlevel"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="电话: " prop="phone">
            <el-input v-model="customerForm.phone"></el-input>
          </el-form-item>
          <el-form-item label="地址: " prop="address">
            <el-input v-model="customerForm.address"></el-input>
          </el-form-item>
          <el-form-item label="邮编: " prop="zipcode">
            <el-input v-model="customerForm.zipcode"></el-input>
          </el-form-item>
          <el-form-item label="电子邮箱: " prop="email">
            <el-input v-model="customerForm.email"></el-input>
          </el-form-item>
          <el-form-item label="应收额度: " prop="lineOfCredit">
            <el-input v-model="customerForm.lineOfCredit"></el-input>
          </el-form-item>
          <el-form-item label="应收: " prop="receivable">
            <el-input v-model="customerForm.receivable"></el-input>
          </el-form-item>
          <el-form-item label="应付: " prop="payable">
            <el-input v-model="customerForm.payable"></el-input>
          </el-form-item>

        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm('customerForm')">立即创建</el-button>
      </span>
    </el-dialog>
  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import { getAllCustomer } from "@/network/purchase";
import { createCustomer } from '@/network/sale';
import { deleteCustomer } from '@/network/sale';
export default {
  name: 'CustomerView',
  components: {
    Layout,
    Title
  },
  data() {
    return {
      customerList: [],
      dialogVisible : false,
      customertype:["供应商","销售商"],
      customerlevel:[1,2,3,4,5],
      customerForm:{
        id:'',
        name:'',
        type:'',
        level:'',
        phone:'',
        address:'',
        zipcode:'',
        email:'',
        lineOfCredit:'',
        receivable:'',
        payable:'',
        operator:'',
      },

      rules: {
        id: [
          { required: true, message: '请输入id', trigger: 'change' }
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'change' }
        ],
        type: [
          { required: true, message: '请选择客户类型', trigger: 'change' }
        ],
        level: [
          { required: true, message: '请选择客户级别', trigger: 'change' }
        ],
        phone: [
          { required: true, message: '请输入客户电话', trigger: 'change' }
        ],
        lineOfCredit: [
          { required: true, message: '请输入应收额度', trigger: 'change' }
        ],
        receivable: [
          { required: true, message: '请输入应收（元）', trigger: 'change' }
        ],
        payable: [
          { required: true, message: '请输入应付（元）', trigger: 'change' }
        ]
      },
    }
  },
  async mounted() {
    await getAllCustomer({ params : { type: 'SUPPLIER' } }).then(_res => {
      this.customerList = this.customerList.concat(_res.result)
    })
    await getAllCustomer({ params : { type: 'SELLER' } }).then(_res => {
      this.customerList = this.customerList.concat(_res.result)
    })
  },
  methods: {
    filterTag(value, row) {
      return row.type === value
    },
    submitForm(formName){
      this.$refs[formName].validate((valid) => {
            if(valid){
              this.customerForm.operator=sessionStorage.getItem("name")
              this.customerForm.id=Number(this.customerForm.id)
              this.customerForm.lineOfCredit=Number(this.customerForm.lineOfCredit)
              this.customerForm.receivable=Number(this.customerForm.receivable)
              this.customerForm.payable=Number(this.customerForm.payable)
              this.customerForm.level=Number(this.customerForm.level)
              createCustomer(this.customerForm).then(_res => {
                if (_res.msg === 'Success') {
                  this.$message.success('创建成功!')
                  this.dialogVisible = false
                }
              })
            }
          }
      )
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            this.resetForm()
            done();
          })
          .catch(_ => {});
    },
    resetForm(){
      this.customerForm = {}
    },
    deletecustomer(id) {
      let config = {
        params: {
          id: Number(id)
        }
      };
      this.$confirm('是否要删除该客户？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() =>{
        deleteCustomer(config).then(_res =>{
          if (_res.msg === 'Success') {
            this.$message.success('删除成功!')
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })

    }
  }
}
</script>

<style>

</style>