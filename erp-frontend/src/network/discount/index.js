import request from "@/network/request";
const testAPI = require("@/apis"); //此处务必使用require导入（因为是module.exports导出的...）

export const makeDiscount = config=>request._post(testAPI.MAKE_DISCOUNT,config)
export const showDiscount = config=>request._get(testAPI.SHOW_DISCOUNT,config)
export const deleteDiscount = config=>request._delete(testAPI.DELETE_DISCOUNT,config)
