import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/login/Login'
import Dashboard from '@/components/layout/Dashboard'

Vue.use(Router)
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/dashboard',
      name: 'Dashboard',
      component: Dashboard,
      redirect: '/personal',
      children: [{
        path: '/personal',
        name: 'Personal',
        component: () => import('@/views/user/Personal')
      }, {
        path: '/applyForLeave',
        name: 'ApplyForLeave',
        component: () => import('@/views/apply-for/ApplyForLeave')
      }, {
        path: '/applyForWork',
        name: 'ApplyForWork',
        component: () => import('@/views/apply-for/ApplyForWork')
      }, {
        path: '/applyForBusiness',
        name: 'ApplyForBusiness',
        component: () => import('@/views/apply-for/applyForBusiness')
      }, {
        path: '/applyForBackCard',
        name: 'ApplyForBackCard',
        component: () => import('@/views/apply-for/ApplyForBackCard')
      }, {
        path: '/applyForBackHoliday',
        name: 'ApplyForBackHoliday',
        component: () => import('@/views/apply-for/ApplyForBackHoliday')
      }, {
        path: '/messageTip',
        name: 'MessageTip',
        component: () => import('@/views/message/MessageTip')
      }, {
        path: '/applyTaskList',
        name: 'ApplyTaskList',
        component: () => import('@/views/apply-task/ApplyTaskList')
      }, {
        path: '/messagePro',
        name: 'MessagePro',
        component: () => import('@/views/message/MessagePro')
      }, {
        path: '/excelExport',
        name: 'ExcelExport',
        component: () => import('@/views/report/ExcelExport')
      }, {
        path: '/calendar',
        name: 'Calendar',
        component: () => import('@/views/calendar/Calendar')
      }, {
        path: '/userInfo',
        name: 'UserInfo',
        component: () => import('@/views/user/UserInfo')
      }, {
        path: '/test',
        name: 'Test',
        component: () => import('@/views/test/Test')
      }]
    }
  ]
})
