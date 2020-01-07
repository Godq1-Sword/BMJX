// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from './util/axios-util'
import './style/index.scss'

// 关闭生产提示
Vue.config.productionTip = false
// 插件导入
Vue.use(ElementUI)
Vue.use(axios)
Vue.prototype.axios = axios
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
