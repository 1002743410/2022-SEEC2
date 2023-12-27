import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import "@/assets/css/normalize.css";
import "@/assets/css/base.css";
import "@/plugins/elementui";
import JsonExcel from 'vue-json-excel';
import Blob from './vendor/Blob';
import Export2Excel from './vendor/Export2Excel.js';
import Vuex from 'vuex';
Vue.use(Vuex);

Vue.component('downloadExcel', JsonExcel);
Vue.prototype.HOST = "/api";
Vue.config.productionTip = false;
Vue.prototype.$bus = new Vue(); //用于取代Vuex实现组件之间的消息传递

const store = new Vuex.Store({
  state:{
    username:''
  },
  mutations:{
    setUsername(state,username){
      state.username=username;
    }
  },
  getters:{
    username:state => state.username
  }
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
