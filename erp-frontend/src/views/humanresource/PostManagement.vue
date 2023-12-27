<template>
  <Layout>
    <Title title="岗位信息"></Title>
    <el-button type="primary" size="medium" @click="dialogVisible = true" >新增岗位信息</el-button>
    <div style="margin-top: 10px" class = "body">
      <el-table
          :data="postList"
          stripe
          style="width: 100%"
          :header-cell-style="{'text-align':'center'}"
          :cell-style="{'text-align':'center'}">
        <el-table-column
            prop="postname"
            label="岗位名"
            width="100">
        </el-table-column>
        <el-table-column
            prop="basicsalary"
            label="基本工资"
            width="200">
        </el-table-column>
        <el-table-column
            prop="postsalary"
            label="岗位工资"
            width="200">
        </el-table-column>
        <el-table-column
            prop="role"
            label="岗位级别"
            width="100">
        </el-table-column>
        <el-table-column
            prop="salarycalculation"
            label="工资计算方式"
            width="200">
        </el-table-column>
        <el-table-column
            prop="salarygrant"
            label="工资发放方式"
            width="150">
        </el-table-column>
        <el-table-column
            prop="tax"
            label="税务计算方式"
            width="180">
        </el-table-column>
      </el-table>
    </div>

    <el-dialog
        title="新增岗位信息"
        :visible.sync="dialogVisible"
        width="40%"
        :before-close="handleClose">
      <div style="width: 90%; margin: 0 auto">
        <el-form :model="postForm" label-width="100px" ref="postForm" :rules="rules">
          <el-form-item label="岗位名: " prop="sex">
            <el-select v-model="postForm.postname" placeholder="请选择岗位名">
              <el-option
                  v-for="item in postnameType"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="基本工资: " prop="basicsalary">
            <el-input v-model="postForm.basicsalary"></el-input>
          </el-form-item>
          <el-form-item label="岗位工资: " prop="postsalary">
            <el-input v-model="postForm.postsalary"></el-input>
          </el-form-item>
          <el-form-item label="岗位级别: " prop="role">
            <el-select v-model="postForm.role" placeholder="请选择员工岗位">
              <el-option
                  v-for="item in roleType"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="工资计算方式: " prop="salarycalculation">
            <el-select v-model="postForm.salarycalculation" placeholder="请选择工资发放方式">
              <el-option
                  v-for="item in salarycalculationType"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="工资发放方式: " prop="salarygrant">
            <el-select v-model="postForm.salarygrant" placeholder="请选择工资计算方式">
              <el-option
                  v-for="item in salarygrantType"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="税务计算方式: " prop="tax">
            <el-select v-model="postForm.tax" placeholder="请选择税务计算方式">
              <el-option
                  v-for="item in taxType"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm()">立即创建</el-button>
      </span>
    </el-dialog>
  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import {createPost,getAllPosts} from "../../network/humanresource";
export default {
  components: {
    Layout,
    Title
  },
  data(){
    return{
      postList:[],
      dialogVisible : false,
      postForm:{
        postname: '',
        basicsalary: '',
        postsalary: '',
        role: '',
        salarycalculation: '',
        salarygrant: '',
        tax: '',
      },
      postnameType:["库存管理人员","进货销售人员","销售经理","财务人员","人力资源人员","总经理","最高权限"],
      roleType:["INVENTORY_MANAGER","SALE_STAFF","SALE_MANAGER","FINANCIAL_STAFF","HR","GM","ADMIN"],
      salarycalculationType:["每月基本工资+岗位工资","每月基本工资+提成","年薪"],
      salarygrantType:["每月一结","每年一结"],
      taxType:["个人所得税"]
    }
  },
  async mounted() {
    await getAllPosts().then(_res => {
      this.postList = this.postList.concat(_res.result)
    })
  },
  methods:{
    submitForm(){
      this.postForm.basicsalary = Number(this.postForm.basicsalary)
      this.postForm.postsalary = Number(this.postForm.postsalary)
      createPost(this.postForm).then(_res => {
        if (_res.msg === 'Success') {
          this.$message.success('创建成功!')
          this.dialogVisible = false
        }
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
      this.dialogVisible=false;
      this.postForm={
        postname: '',
            basicsalary: '',
            postsalary: '',
            role: '',
            salarycalculation: '',
            salarygrant: '',
            tax: '',
      }
    },
  }
};
</script>

<style scoped>
</style>