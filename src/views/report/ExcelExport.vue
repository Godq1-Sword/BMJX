<template>
  <el-card class="box-card">
    <span class="exportSpan">个人考勤信息</span>
    <el-form label-position="top" label-width="80px">
      <el-form-item>
        <span slot="label" class="label">导出日期范围</span>
        <el-date-picker
          v-model="exportTime"
          type="daterange"
          :picker-options="pickerOptions"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          align="right">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="downloadExcel()">导出Excel</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
import elementUiUtil from '../../util/elementui-util'
import Router from '../../router'

export default {
  data () {
    return {
      rules: {
        exportTime: {
          required: true,
          message: '导出日期范围不能为空'
        }
      },
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }],
        disabledDate (time) {
          return time.getTime() > Date.now()
        }
      },
      exportTime: ''
    }
  },
  methods: {
    downloadExcel () {
      if (this.exportTime[0] != null && this.exportTime[1] != null) {
        let startTimeDate = new Date(this.exportTime[0])
        let startTimeStr = startTimeDate.getFullYear() + '-' + this.transfrom10(startTimeDate.getMonth() + 1) + '-' + this.transfrom10(startTimeDate.getDate())
        let endTimeDate = new Date(this.exportTime[1])
        let endTimeStr = endTimeDate.getFullYear() + '-' + this.transfrom10(endTimeDate.getMonth() + 1) + '-' + this.transfrom10(endTimeDate.getDate())
        let xhr = new XMLHttpRequest()
        xhr.open('GET', '/api/excel/user?startTime=' + startTimeStr + '&endTime=' + endTimeStr, true)
        xhr.setRequestHeader('Authorization', localStorage.getItem('token'))
        xhr.responseType = 'blob'
        xhr.onload = function () {
          if (this.status === 200) {
            // 如果请求执行成功
            var blob = this.response
            var a = document.createElement('a')
            var url = window.URL.createObjectURL(blob)
            a.href = url
            console.log(xhr.getResponseHeader('content-disposition'))
            a.download = decodeURIComponent(xhr.getResponseHeader('content-disposition').split(';')[1].split('filename=')[1])
            // 火狐浏览器 必须把元素插入body中
            document.body.appendChild(a)
            a.click()
            document.body.removeChild(a)
            // 释放之前创建的URL对象
            window.URL.revokeObjectURL(url)
          } else if (this.status === 403) {
            elementUiUtil.Message({
              showClose: true,
              message: '身份验证过期,请重新登录',
              type: 'error'
            })
            // 清除token
            localStorage.removeItem('token')
            // 跳转登录页面，并将要浏览的页面fullPath传过去，登录成功后跳转需要访问的页面
            setTimeout(() => {
              Router.replace({
                path: '/login',
                query: {
                  redirect: Router.currentRoute.fullPath
                }
              }, 1000)
            })
          }
        }
        xhr.send()
        // let a = document.createElement('a')
        // a.href = '/api/excel/user?startTime=' + startTimeStr + '&endTime=' + endTimeStr
        // a.click()
      } else {
        elementUiUtil.Message('日期范围不能为空', 'error')
      }
    },
    transfrom10 (value) {
      return value < 10 ? '0' + value : value
    }
  }
}
</script>
<style rel="stylesheet/scss" xml:lang="scss">
  .el-card__body {
    height: 100%;
    padding: 5%;
  }
</style>
<style rel="stylesheet/scss" xml:lang="scss" scoped>
  .box-card {
    width: 90%;
    height: 100%;
    padding-left: 10%;
  }

  .exportSpan {
    display: block;
    margin-bottom: 10px;
    font-size: 24px;
    border-radius: 15px;
  }

  .label {
    font-size: 16px;
  }
</style>
