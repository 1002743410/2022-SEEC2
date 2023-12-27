import request from "@/network/request";
const testAPI = require("@/apis"); //此处务必使用require导入（因为是module.exports导出的...）

export const  receiptApproval=config=>request._get(testAPI.RECEIPT_APPROVAL,config)
export const receiptMake = config=>request._post(testAPI.RECEIPT_MAKE,config)
export const receiptShow = config=>request._get(testAPI.RECEIPT_SHOW,config)
export const receiptFind = config=>request._get(testAPI.RECEIPT_FIND,config)
export const receiptChange = config=>request._get(testAPI.RECEIPT_CHANGE,config)

export const  payApproval=config=>request._get(testAPI.PAY_APPROVAL,config)
export const payMake = config=>request._post(testAPI.PAY_MAKE,config)
export const payShow = config=>request._get(testAPI.PAY_SHOW,config)
export const payFind = config=>request._get(testAPI.PAY_FIND,config)
export const payChange = config=>request._get(testAPI.PAY_CHANGE,config)

export const createBank=config=>request._get(testAPI.BANK_CREATE,config)
export const findBank = config=>request._get(testAPI.BANK_FIND,config)
export const updateBank = config=>request._get(testAPI.BANK_UPDATE,config)
export const deleteBank = config =>request._get(testAPI.BANK_DELETE,config)
export const decreaseBank = config=>request._get(testAPI.BANK_DECREASE,config)