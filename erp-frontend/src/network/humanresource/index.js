import request from "@/network/request";
const testAPI = require("@/apis")
export const createCheck = config => request._post(testAPI.CREATE_CHECK, config);
export const getdateChecks = config => request._get(testAPI.GET_DATE_CHECKS, config);
export const getdateUncheck = config => request._get(testAPI.GET_DATE_UNCHECK, config);
export const getdaterangeCheck = config => request._get(testAPI.GET_DATE_RANGE_CHECK, config);
export const createworker = config => request._post(testAPI.CREATE_WORKER, config);
export const getAllWorkers = config => request._get(testAPI.GET_ALL_WORKERS, config);
export const createPost = config => request._post(testAPI.CREATE_POST, config);
export const getAllPosts = config => request._get(testAPI.GET_ALL_POSTS, config);
export const getSalaryStrategy = config => request._get(testAPI.GET_SALARY_STRATEGY, config);
export const createSalaryList = config => request._post(testAPI.CREATE_SALARY_LIST, config);
export const getSalaryList = config => request._get(testAPI.GET_SALARY_LIST, config);

export const chooseSalaryList = config =>request._get(testAPI.CHOOSE_SALARY_LIST,config);
export const updateSalaryListState = config=>request._get(testAPI.UPDATE_SALARY_LIST,config);