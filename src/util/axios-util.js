import Axios from 'axios'
import {Message} from 'element-ui'
import Router from '../router'
import QS from 'qs'
// 超时响应
Axios.defaults.timeout = 10000
// 配置请求头
Axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
// 环境基础URL
Axios.defaults.baseURL = '/api'

// 跳转登录页面 - 登录返回处理页面
function toLogin () {
  // eslint-disable-next-line no-undef
  // console.log(this.router.query)
  Router.replace({
    path: '/login',
    query: {
      redirect: Router.currentRoute.fullPath
    }
  })
}

// 请求拦截器
Axios.interceptors.request.use(
  request => {
    let token = localStorage.getItem('token')
    token && (request.headers.Authorization = token)
    return request
  },
  error => Promise.error(error))

// 响应拦截器
Axios.interceptors.response.use(response => {
  console.log(response)
  if (response.data.status === 200) {
    // 原封不动封装返回
    return Promise.resolve(response)
  } else {
    // 推送下一步
    return Promise.reject(response)
  }
}, error => {
  if (error.response.status) {
    switch (error.response.status) {
      // 401:未登录 - 返回登陆携带页面路径
      case 401:
        toLogin()
        break
      // 403:token过期 - 清除token返回登录
      case 403:
        Message({
          showClose: true,
          message: '身份验证过期,请重新登录',
          type: 'error'
        })
        // 清除token
        localStorage.removeItem('token')
        // 跳转登录页面，并将要浏览的页面fullPath传过去，登录成功后跳转需要访问的页面
        setTimeout(() => {
          toLogin()
        }, 1000)
        break
      // 404请求不存在
      case 404:
        Message({
          showClose: true,
          message: '请求页面不存在',
          type: 'error'
        })
        break
      // 其他错误
      case 500:
        Message({
          showClose: true,
          message: '服务器错误',
          type: 'error'
        })
        break
    }
    return Promise.reject(error.response)
  }
})
export default {
  // Get请求
  get (url, params) {
    return new Promise((resolve, reject) => {
      Axios.get(url, {
        params: params
      }).then(res => {
        resolve(res.data)
      }).catch(err => {
        reject(err.data)
      })
    })
  },
  // Post请求
  post (url, params) {
    return new Promise((resolve, reject) => {
      Axios.post(url, QS.stringify(params))
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err.data)
        })
    })
  },
  // Put请求
  put (url, params) {
    return new Promise((resolve, reject) => {
      Axios.put(url, QS.stringify(params))
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err.data)
        })
    })
  },
  // Delete请求
  del (url, params) {
    return new Promise((resolve, reject) => {
      Axios.delete(url, {data: params})
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err.data)
        })
    })
  }
}
