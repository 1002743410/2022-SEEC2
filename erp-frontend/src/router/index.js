import Vue from "vue";
import VueRouter from "vue-router";
import { ROLE, PATH } from "../common/const";

const Error = () => import("../components/content/Error");
const Login = () => import("../views/auth/Login");
const Home = () => import("../views/Home");
const CommodityManagement = () =>
  import("../views/commodity/CommodityManagement");
const CommodityClassification = () =>
  import("../views/commodity/CommodityClassification");
const InventoryManagement = () => import("../views/inventory/InventoryManagement");
const InventoryCheck = () => import("../views/inventory/InventoryCheck");
const InventoryOperation = () =>
  import("../views/inventory/InventoryOperation");
const InventoryIn = () => import("../views/inventory/InventoryIn");
const InventoryOut = () => import("../views/inventory/InventoryOut");
const InventoryLoss = () => import("../views/inventory/InventoryLoss");
const InventoryOverflow = () => import("../views/inventory/InventoryOverflow");
const InventoryPresent = () => import("../views/inventory/InventoryPresent");
const InventoryView = () => import("../views/inventory/InventoryView");
const InventoryWarning = () => import("../views/inventory/InventoryWarning");
const PurchaseView = () => import("../views/purchase/PurchaseView");
const PurchaseReturnView = () => import("../views/purchase/PurchaseReturnView");
const SaleView = () => import("../views/sale/SaleView");
const CustomerView = () => import ("../views/purchase/CustomerView");
const Approval = () => import("../views/approval/Approval");
const CheckSaleDetail = () => import("../views/checkList/checkSaleDetail");
const SaleReturnView = () => import("../views/sale/SaleReturnView");
const CheckCondition = ()=>import("../views/checkList/checkCondition");
const CheckCourse = ()=>import("../views/checkList/checkCourse");
const MakeDiscount = ()=>import("../views/discount/makeDiscount");
const FinanceSheet = ()=>import("../views/finance/financeSheet");
const BankManange =()=>import("../views/finance/bankManage");
const Salarystrategy = () =>
    import ("../views/humanresource/SalaryStrategy");
const Staffcheck = () =>
    import ("../views/humanresource/StaffCheck");
const Staffmanagement = () =>
    import ("../views/humanresource/StaffManagement");
const CheckIn = () =>
    import ("../views/humanresource/CheckIn");
const PostManagement = () =>
    import ("../views/humanresource/PostManagement");
Vue.use(VueRouter);

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    component: Home
  },
  {
    path: "/error",
    component: Error
  },
  {
    path: "/login",
    component: Login
  },
  {
    path: "/register",
    component: () => import("@/views/auth/register.vue")
  },
  // 商品管理
  {
    path: PATH.COMMODITY_CLASSIFICATION.path,
    component: CommodityClassification,
    meta: { requiresAuth: PATH.COMMODITY_CLASSIFICATION.requiresAuth }
  },
  {
    path: PATH.COMMODITY_MANAGEMENT.path,
    component: CommodityManagement,
    meta: { requiresAuth: PATH.COMMODITY_MANAGEMENT.requiresAuth }
  },
  // 库存管理
  {
    path: PATH.INVENTORY_MANAGEMENT.path,
    component: InventoryManagement,
    meta: { requiresAuth: PATH.INVENTORY_MANAGEMENT.requiresAuth }
  },
  {
    path: PATH.INVENTORY_CHECK.path,
    component: InventoryCheck,
    meta: { requiresAuth: PATH.INVENTORY_CHECK.requiresAuth }
  },
  {
    path: PATH.INVENTORY_OPERATION.path,
    component: InventoryOperation,
    name: "InventoryOperation",
    meta: { requiresAuth: PATH.INVENTORY_OPERATION.requiresAuth }
  },
  {
    path: PATH.INVENTORY_IN.path,
    component: InventoryIn,
    name: "InventoryIn",
    meta: { requiresAuth: PATH.INVENTORY_LOSS.requiresAuth }
  },
  {
    path: PATH.INVENTORY_OUT.path,
    component: InventoryOut,
    name: "InventoryOut",
    meta: { requiresAuth: PATH.INVENTORY_LOSS.requiresAuth }
  },
  {
    path: PATH.INVENTORY_LOSS.path,
    component: InventoryLoss,
    name: "InventoryLoss",
    meta: { requiresAuth: PATH.INVENTORY_LOSS.requiresAuth }
  },
  {
    path: PATH.INVENTORY_OVERFLOW.path,
    component: InventoryOverflow,
    name: "InventoryOverflow",
    meta: { requiresAuth: PATH.INVENTORY_OVERFLOW.requiresAuth }
  },
  {
    path: PATH.INVENTORY_PRESENT.path,
    component: InventoryPresent,
    name: "InventoryPresent",
    meta: { requiresAuth: PATH.INVENTORY_PRESENT.requiresAuth }
  },
  {
    path: PATH.INVENTORY_VIEW.path,
    component: InventoryView,
    meta: { requiresAuth: PATH.INVENTORY_VIEW.requiresAuth }
  },
  {
    path: PATH.INVENTORY_WARNING.path,
    component: InventoryWarning,
    name: "InventoryWarning",
    meta: { requiresAuth: PATH.INVENTORY_WARNING.requiresAuth }
  },
  // 销售管理
  {
    path: PATH.PURCHASE_VIEW.path,
    component: PurchaseView,
    name: "PurchaseView",
    meta: { requiresAuth: PATH.PURCHASE_VIEW.requiresAuth }
  },
  {
    path: PATH.PURCHASE_RETURN_VIEW.path,
    component: PurchaseReturnView,
    name: "PurchaseReturnView",
    meta: { requiresAuth: PATH.PURCHASE_RETURN_VIEW.requiresAuth }
  },
  {
    path: PATH.SALE_VIEW.path,
    component: SaleView,
    name: "SaleView",
    meta: { requiresAuth: PATH.SALE_VIEW.requiresAuth }
  },
  //销售退货
  {
    path: PATH.SALE_RETURN_VIEW.path,
    component:SaleReturnView,
    name: "SaleReturnView",
    meta: {requiresAuth: PATH.SALE_RETURN_VIEW.requiresAuth}
  },
  {
    path: PATH.CUSTOMER_VIEW.path,
    component: CustomerView,
    name: "CustomerView",
    meta: { requiresAuth: PATH.CUSTOMER_VIEW.requiresAuth }
  },
  // 审批
  {
    path: PATH.GM_APPROVAL.path,
    component: Approval,
    meta: { requiresAuth: PATH.GM_APPROVAL.requiresAuth }
  },
  //查看三种表单
  {
    path: PATH.CHECK_SD.path,
    component: CheckSaleDetail,
    meta:{requiresAuth: PATH.CHECK_SD.requiresAuth}
  },
  {
    path: PATH.CHECK_CD.path,
    component: CheckCondition,
    meta: {requiresAuth: PATH.CHECK_CD.requiresAuth}
  },
  {
    path: PATH.CHECK_CC.path,
    component: CheckCourse,
    meta: {requiresAuth: PATH.CHECK_CC.requiresAuth}

  },
    //优惠政策
  {
    path: PATH.DISCOUNT_MAKE.path,
    component: MakeDiscount,
    meta: {requiresAuth:PATH.DISCOUNT_MAKE.requiresAuth}
  },
    //财务单据制定
  {
    path: PATH.FINANCE_SHEET.path,
    component:FinanceSheet,
    meta: {requiresAuth: PATH.FINANCE_SHEET.requiresAuth}
  },
  {
    path: PATH.BANK_MANAGE.path,
    component:BankManange,
    meta: {requiresAuth: PATH.FINANCE_SHEET.requiresAuth}
  },

  //人力资源管理
  {
    path: PATH.SALARY_STRATEGY.path,
    component: Salarystrategy,
    meta: { requiresAuth: PATH.SALARY_STRATEGY.requiresAuth }
  },
  {
    path: PATH.STAFF_CHECK.path,
    component: Staffcheck,
    meta: { requiresAuth: PATH.STAFF_CHECK.requiresAuth }
  },
  {
    path: PATH.STAFF_MANAGEMENT.path,
    component: Staffmanagement,
    meta: { requiresAuth: PATH.STAFF_MANAGEMENT.requiresAuth }
  },
  {
    path: PATH.CHECK_IN.path,
    component: CheckIn,
    meta: { requiresAuth: PATH.CHECK_IN.requiresAuth }
  },
  {
    path: PATH.POST_MANAGEMENT.path,
    component: PostManagement,
    meta: { requiresAuth: PATH.POST_MANAGEMENT.requiresAuth }
  },

  // -----------------------未找到页面-----------------------------
  {
    path: "*",
    redirect: "/error"
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

router.beforeEach(async (to, from, next) => {
  // console.log(to.path);
  if (to.path === "/error" || to.path === "/login") {
    next();
  } else if (to.path === "/") {
    let token = sessionStorage.getItem("token");
    let role = sessionStorage.getItem("role");
    if (token == null || role == null) next("/login");
    else next();
  } else if (to.meta.requiresAuth) {
    if (
      to.meta.requiresAuth.some(
        role => role.toString() === sessionStorage.getItem("role")
      )
    ) {
      //有权限
      // console.log("获得访问权限");
      next()
    } else {
      // console.log("无权限访问");
      next("/"); //无权限,跳回主页
    }
  } else {
    // 非法路径, 直接next (跳转error)
    next();
  }
});

export default router;
