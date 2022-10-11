import axios from 'axios'
import router from '../router/index'
import Vue from 'vue';
import { Toast } from 'vant';
import qs from 'qs'

Vue.use(Toast);


// 1. 基础路径
axios.defaults.baseURL = 'http://localhost:8089/'
axios.defaults.headers.post['Content-Type'] = 'application/json';//配置请求头
// axios.defaults.timeout = 1000

// Vue.prototype.axios = axios



// 拦截器

// http request拦截器 添加一个请求拦截器
axios.interceptors.request.use(function (config) {
  return config;
}, function (error) {
  Toast.fail('请求超时');
  return Promise.reject(error);
});

// // 添加一个响应拦截器
axios.interceptors.response.use(function (response) {
  // if (response.data && response.data.code) {
  //   if (response.data.message === 'token失效' || response.data.message === '请求头中没有token') {
  //     //未登录
  //     Toast.fail("登录信息已失效，请重新登录");
  //     router.push('/login');
  //   }
  //   if (parseInt(response.data.code) === -1) {
  //     Toast.fail("请求失败");
  //   }
  // }
  return response;
}, function (error) {
  Toast.fail("服务器连接失败");
  return Promise.reject(error);
})



/**
  get方式请求
*/
export function get(url, params) {
  return axios({
    method: 'get',
    url,
    params, // get 请求时带的参数
    timeout: 5000
  })
}

/**
 * 提交post请求 发送的数据为查询字符串，key=val&key=val
*/
export function post(url, data) {
  return axios({
    method: "post",
    url,
    data: data,
    timeout: 5000,
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

export function put(url, data) {
  return axios({
    method: "put",
    url,
    data: data,
    timeout: 5000,
    headers: {
      'Content-Type': 'application/json'
    }
  })
}


/**
 * 提交post请求 ,查询字符串，对象中嵌套数组的格式
*/
export function post_obj_array(url, data) {
  return axios({
    method: "post",
    url,
    data: qs.stringify(data, { allowDots: true }),
    timeout: 5000
  })
}

/**
 * 提交post请求 发送的数据为查询字符串，当参数为数组的时候适用该方法
 * ids=1&ids=2
*/
export function post_array(url, data) {
  return axios({
    method: "post",
    url,
    data: qs.stringify(data, { arrayFormat: "repeat" }),
    timeout: 5000
  })
}
/**
 * 提交post请求 发送的数据为json字符串
*/
export function post_json(url, data) {
  return axios({
    method: "post",
    url,
    data,
    timeout: 5000
  })
}

export default axios
