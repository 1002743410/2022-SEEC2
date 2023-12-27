import request from "@/network/request";
const testAPI = require("@/apis"); //此处务必使用require导入（因为是module.exports导出的...）


export const stub1 = config =>request._post(testAPI.CHECKLIST_STUB1,config)
export const getSDByTime = config=>request._get(testAPI.CHECKLIST_SDTIME,config)
export const getSDByMessage = config=>request._get(testAPI.CHECKLIST_SDMESS,config)
export const getCCByTime = config=>request._get(testAPI.CHECKLIST_CCTIME,config)
export const getCCByMessage = config=>request._get(testAPI.CHECKLIST_CCMESSAGE,config)
export const getCondition = config=>request._get(testAPI.CHECKLIST_CONDITION,config)