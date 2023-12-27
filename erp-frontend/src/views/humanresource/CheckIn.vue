<template>
  <Layout>
    <Title title="打卡"></Title>
    <el-button type="primary" size="big" @click="submitcheck()" :disabled="submissionFlag">签到</el-button>
  </Layout>
</template>

<script>
  import Layout from "@/components/content/Layout";
  import Title from "@/components/content/Title";
  import { createCheck } from '../../network/humanresource';
  export default {
    components: {
        Layout,
        Title
    },
    data() {
      return{
          checkinformance:{
              name: '',
              role: '',
              checktime: '',
            },
            submissionFlag:false,
        }
    },
    methods:{
        submitcheck(){
            this.checkinformance.name= sessionStorage.getItem("name")
            this.checkinformance.role=sessionStorage.getItem("role")
            let datetime = new Date();
            let year=datetime.getFullYear();
            let month=datetime.getMonth() + 1;
            let day=datetime.getDate();
            let currentday = '';
            if(month<10){
                if(day<10){
                    currentday = year + "-0" + month + "-0" + day;
                }else{
                    currentday = year + "-0" + month + day;
                }
            }else{
                if(day<10){
                    currentday = year + "-" +month + "-0" + day;
                }else{
                    currentday = year + "-" + month + "-" + day;
                }
            }
            this.checkinformance.checktime = currentday;
            createCheck(this.checkinformance).then(_res => {
            if (_res.msg === 'Success') {
              this.$message.success('打卡成功!')
              this.submissionFlag = true;
            }
          })
        }
    }
  };
</script>

<style scoped>
</style>