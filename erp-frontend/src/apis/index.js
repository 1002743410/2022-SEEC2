//TEST
const TEST_GET = "/api/test/get";
const TEST_POST = "/api/test/post";

const AUTH = "/api/user/auth"
const LOGIN = "/api/user/login"
const REGISTER = '/api/user/register'

// 商品分类管理
const COMMODITY_CLASSIFICATION_ALL = '/api/category/queryAll'
const COMMODITY_CLASSIFICATION_CREATE = '/api/category/create'
const COMMODITY_CLASSIFICATION_UPDATE = '/api/category/update'
const COMMODITY_CLASSIFICATION_DELETE = '/api/category/delete'

// 商品管理
const COMMODITY_ALL = '/api/product/queryAll';
const COMMODITY_CREATE = '/api/product/create';
const COMMODITY_UPDATE = '/api/product/update';
const COMMODITY_DELETE = '/api/product/delete';


const WAREHOUSE_INPUT = '/api/warehouse/input';
const WAREHOUSE_OUTPUT_PRE = '/api/warehouse/product/count';
const WAREHOUSE_OUTPUT = '/api/warehouse/output';
const WAREHOUSE_GET_INPUTSHEET = '/api/warehouse/inputSheet/state';
const WAREHOUSE_GET_OUTPUTSHEET = '/api/warehouse/outputSheet/state';
const WAREHOUSE_IO_DEATIL_BY_TIME = '/api/warehouse/sheetContent/time';
const WAREHOUSE_IPQ_BY_TIME = '/api/warehouse/inputSheet/time/quantity';
const WAREHOUSE_OPQ_BY_TIME = '/api/warehouse/outputSheet/time/quantity';
const WAREHOUSE_OUTPUTSHEET_APPROVE = '/api/warehouse/outputSheet/approve';
const WAREHOUSE_INPUTSHEET_APPROVE = '/api/warehouse/inputSheet/approve';
const WAREHOUSE_DAILY_COUNT = '/api/warehouse/warehouse/counting';


// 销售管理
// 进货管理
const PURCHASE_ALL = '/api/purchase/sheet-show';
const PURCHASE_FIND_SHEET_BY_ID = '/api/purchase/find-sheet';
const PURCHASE_CREATE = '/api/purchase/sheet-make';
const PURCHASE_FIRST_APPROVAL = '/api/purchase/first-approval';
const PURCHASE_SECOND_APPROVAL = '/api/purchase/second-approval';
// 进货退货管理
const PURCHASE_RETURN_ALL = '/api/purchase-returns/sheet-show';
const PURCHASE_RETURN_CREATE = '/api/purchase-returns/sheet-make';
const PURCHASE_RETURN_FIRST_APPROVAL = '/api/purchase-returns/first-approval';
const PURCHASE_RETURN_SECOND_APPROVAL = '/api/purchase-returns/second-approval';
// 销售管理
const SALE_ALL = '/api/sale/sheet-show';
const SALE_CREATE = '/api/sale/sheet-make';
const SALE_FIRST_APPROVAL = '/api/sale/first-approval';
const SALE_SECOND_APPROVAL = '/api/sale/second-approval';
const SALE_FIND_SHEET_BY_ID = '/api/sale/find-sheet';
//销售退货管理
const SALE_RETURN_ALL = '/api/sale-returns/sheet-show';
const SALE_RETURN_CREATE = 'api/sale-returns/sheet-make';
const SALE_RETURN_FIRST_APPROVAL = '/api/sale-returns/first-approval';
const SALE_RETURN_SECOND_APPROVAL = '/api/sale-returns/second-approval';
// 客户管理
const CUSTOMER_QUERY = '/api/customer/findByType';
const SALE_PURCHASE_ALL = '/api/purchase/sheet-show';
const SALE_PURCHASE_CREATE = '/api/purchase/sheet-make';
const SALE_CUSTOMER_QUERY = '/api/customer/findByType';
const SALE_CUSTOMER_MAX = '/api/sale/maxAmountCustomer';
const CUSTOMER_CREATE = '/api/customer/create';
const CUSTOMER_DELETE = '/api/customer/delete';

//查看三种表单
const CHECKLIST_STUB1 = '/api/checkList/stub1';
const CHECKLIST_SDTIME = '/api/checkList/saleDetail/time'
const CHECKLIST_SDMESS = '/api/checkList/saleDetail/message'
const CHECKLIST_CCTIME = '/api/checkList/course/time'
const CHECKLIST_CCMESSAGE ='/api/checkList/course/message'
const CHECKLIST_CONDITION = '/api/checkList/condition/time'

//折扣制定
const MAKE_DISCOUNT='/api/discount/discount-make'
const DELETE_DISCOUNT='/api/discount/discount-delete'
const SHOW_DISCOUNT='/api/discount/discount-show'

//财务模块
const RECEIPT_APPROVAL='/api/receipt/second-approval'
const RECEIPT_MAKE='/api/receipt/sheet-make'
const RECEIPT_SHOW='/api/receipt/sheet-show'
const RECEIPT_FIND='/api/receipt/find-sheet'
const RECEIPT_CHANGE='/api/receipt/makeChange'

const PAY_APPROVAL='/api/payment-order/second-approval'
const PAY_MAKE='/api/payment-order/sheet-make'
const PAY_SHOW='/api/payment-order/sheet-show'
const PAY_FIND='/api/payment-order/find-sheet'
const PAY_CHANGE='/api/payment-order/makeChange'

const BANK_CREATE = '/api/bank-account/create'
const BANK_FIND = '/api/bank-account/findAll'
const BANK_UPDATE = '/api/bank-account/update'
const BANK_DELETE = '/api/bank-account/delete'
const BANK_DECREASE = '/api/bank-account/decrease'

const CREATE_CHECK = '/api/humanresource/create';
const GET_DATE_CHECKS = '/api/humanresource/showdatechecks';
const GET_DATE_UNCHECK = '/api/humanresource/showdateuncheck';
const GET_DATE_RANGE_CHECK = '/api/humanresource/showdaterangecheck';
const CREATE_WORKER = '/api/humanresource/createworker';
const GET_ALL_WORKERS = '/api/humanresource/getallworkers';
const CREATE_POST = '/api/humanresource/createpostinformance';
const GET_ALL_POSTS = '/api/humanresource/getallposts';
const GET_SALARY_STRATEGY = '/api/humanresource/getsalarystrategy';
const CREATE_SALARY_LIST = '/api/humanresource/createsalarylist';
const GET_SALARY_LIST = '/api/humanresource/getsalarylist';
const CHOOSE_SALARY_LIST = '/api/humanresource/choose-state';
const UPDATE_SALARY_LIST = '/api/humanresource/update-state';

module.exports = {
  TEST_GET,
  TEST_POST,

  AUTH,
  LOGIN,
  REGISTER,
  COMMODITY_CLASSIFICATION_ALL,
  COMMODITY_CLASSIFICATION_CREATE,
  COMMODITY_CLASSIFICATION_UPDATE,
  COMMODITY_CLASSIFICATION_DELETE,

  COMMODITY_ALL,
  COMMODITY_CREATE,
  COMMODITY_UPDATE,
  COMMODITY_DELETE,

  WAREHOUSE_INPUT,
  WAREHOUSE_OUTPUT_PRE,
  WAREHOUSE_OUTPUT,
  WAREHOUSE_GET_INPUTSHEET,
  WAREHOUSE_GET_OUTPUTSHEET,
  WAREHOUSE_IO_DEATIL_BY_TIME,
  WAREHOUSE_IPQ_BY_TIME,
  WAREHOUSE_OPQ_BY_TIME,
  WAREHOUSE_OUTPUTSHEET_APPROVE,
  WAREHOUSE_INPUTSHEET_APPROVE,
  WAREHOUSE_DAILY_COUNT,

  PURCHASE_ALL,
  PURCHASE_CREATE,
  PURCHASE_FIRST_APPROVAL,
  PURCHASE_SECOND_APPROVAL,
  PURCHASE_RETURN_ALL,
  PURCHASE_RETURN_CREATE,
  PURCHASE_RETURN_FIRST_APPROVAL,
  PURCHASE_RETURN_SECOND_APPROVAL,
  PURCHASE_FIND_SHEET_BY_ID,

  SALE_ALL,
  SALE_CREATE,
  SALE_FIRST_APPROVAL,
  SALE_SECOND_APPROVAL,
  SALE_RETURN_ALL,
  SALE_RETURN_CREATE,
  SALE_RETURN_FIRST_APPROVAL,
  SALE_RETURN_SECOND_APPROVAL,
  SALE_CUSTOMER_QUERY,
  SALE_CUSTOMER_MAX,
  SALE_FIND_SHEET_BY_ID,
  CUSTOMER_QUERY,
  CUSTOMER_CREATE,
  CUSTOMER_DELETE,

  CHECKLIST_STUB1,
  CHECKLIST_SDTIME,
  CHECKLIST_SDMESS,
  CHECKLIST_CCTIME,
  CHECKLIST_CCMESSAGE,
  CHECKLIST_CONDITION,

  MAKE_DISCOUNT,
  DELETE_DISCOUNT,
  SHOW_DISCOUNT,

  RECEIPT_APPROVAL,
  RECEIPT_MAKE,
  RECEIPT_SHOW,
  RECEIPT_FIND,
  RECEIPT_CHANGE,

  PAY_APPROVAL,
  PAY_MAKE,
  PAY_SHOW,
  PAY_FIND,
  PAY_CHANGE,

  BANK_CREATE ,
  BANK_FIND,
  BANK_UPDATE,
  BANK_DELETE,
  BANK_DECREASE,

  CREATE_CHECK,
  GET_DATE_CHECKS,
  GET_DATE_UNCHECK,
  GET_DATE_RANGE_CHECK,
  CREATE_WORKER,
  GET_ALL_WORKERS,
  CREATE_POST,
  GET_ALL_POSTS,
  GET_SALARY_STRATEGY,
  CREATE_SALARY_LIST ,
  GET_SALARY_LIST,
  CHOOSE_SALARY_LIST,
  UPDATE_SALARY_LIST
};
