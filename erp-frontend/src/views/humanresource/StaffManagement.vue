<template>
  <Layout>
    <Title title="员工信息"></Title>
    <el-button type="primary" size="medium" @click="dialogVisible = true" >新增员工信息</el-button>
    <div style="margin-top: 10px" class = "body">
      <el-table
        :data="workerList"
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
          prop="sex"
          label="性别"
          width="50">
        </el-table-column>
        <el-table-column
          prop="birthday"
          label="出生日期"
          width="200">
        </el-table-column>
        <el-table-column
          prop="role"
          label="岗位"
          width="100">
        </el-table-column>
        <el-table-column
          prop="inductionday"
          label="入职日期"
          width="200">
        </el-table-column>
        <el-table-column
          prop="phone"
          label="电话"
          width="150">
        </el-table-column>
        <el-table-column
          prop="email"
          label="邮箱"
          width="150">
        </el-table-column>
        <el-table-column
          prop="salarycard"
          label="工资卡账户"
          width="180">
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
      title="新增员工信息"
      :visible.sync="dialogVisible"
      width="40%"
      :before-close="handleClose">
      <div style="width: 90%; margin: 0 auto">
      <el-form :model="workerForm" label-width="100px" ref="workerForm" :rules="rules">
          <el-form-item label="姓名: " prop="name">
            <el-input v-model="workerForm.name"></el-input>
          </el-form-item>
          <el-form-item label="性别: " prop="sex">
            <el-select v-model="workerForm.sex" placeholder="请选择员工性别">
            <el-option
                v-for="item in sexType"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="出生日期: " prop="birthday">
            <el-input v-model="workerForm.birthday"></el-input>
          </el-form-item>
          <el-form-item label="岗位: " prop="role">
            <el-select v-model="workerForm.role" placeholder="请选择员工岗位">
             <el-option
                v-for="item in roleType"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="入职日期: " prop="inductionday">
            <el-input v-model="workerForm.inductionday"></el-input>
          </el-form-item>
          <el-form-item label="电话: " prop="phone">
            <el-input v-model="workerForm.phone"></el-input>
          </el-form-item>
          <el-form-item label="电子邮箱: " prop="email">
            <el-input v-model="workerForm.email"></el-input>
          </el-form-item>
          <el-form-item label="工资卡账户：" prop="salaycard">
            <el-input v-model="workerForm.salarycard"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm('workerForm')">立即创建</el-button>
      </span>
    </el-dialog>
  </Layout>
</template>

<script>
  import Layout from "@/components/content/Layout";
  import Title from "@/components/content/Title";
  import {createworker,getAllWorkers} from "../../network/humanresource";
  import { register } from "@/network/auth";
  export default {
    components: {
        Layout,
        Title
    },
    data(){
      return{
        workerList:[],
        dialogVisible : false,
        sexType : ["男","女"],
        roleType:["INVENTORY_MANAGER","SALE_STAFF","SALE_MANAGER","FINANCIAL_STAFF","HR","GM","ADMIN"],
        workerForm:{
          name: '',
          sex: '',
          birthday: '',
          role: '',
          inductionday: '',
          phone: '',
          email: '',
          salarycard: '',
        }
      }
    },
    rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'change' }
        ],
        sex: [
          { required: true, message: '请选择员工性别', trigger: 'change' }
        ],
        role: [
          { required: true, message: '请选择员工职务', trigger: 'change' }
        ],
        phone: [
          { required: true, message: '请输入员工电话', trigger: 'change' }
        ]
      },
    async mounted() {
    await getAllWorkers().then(_res => {
      this.workerList = this.workerList.concat(_res.result)
    })
  },
    methods:{
        submitForm(formName){
        this.$refs[formName].validate((valid) => {
        if(valid){
          createworker(this.workerForm).then(_res => {
            if (_res.msg === 'Success') {
              this.$message.success('创建成功!')
              let params =  {
                name: this.workerForm.name,
                role: this.workerForm.role,
                password: "123456"
              };
              register(params)
              this.dialogVisible = false
            }
          })
        }
      }
      )
        }
      }
  };
</script>

<style scoped>
</style>