<template>
  <el-container style="height:100%;padding:0;margin:0;width:100%;">
    <el-header style="height: 120px;background: #416bbd;vertical-align: center">
      <el-image :src="topLogoUrl" style="margin-left: 25px;margin-top: 20px;float: left"></el-image>
      <span style="float: left;margin-top: 38px;margin-left: 15px;color: #ffffff;font-size: 28px;font-family:cursive;">大为数据互联考勤系统</span>
    </el-header>
    <el-main>
      <el-image :src="mainLogoUrl" style="margin-left: 10%;margin-top: 5%;float: left"></el-image>
      <div style="width: 30%;height: 100%;float: left;margin-top:15%;margin-left: 20px">
        <span
          style="font-family: cursive;font-size: 100px;color: #416bbd;font-weight: bold">为国聚财<br>&nbsp;&nbsp;为民收税</span>
      </div>
      <el-card style="width: 300px;height: 400px;float: left;margin-top: 5%;margin-left: 5%;border-radius: 15px">
        <el-tabs :stretch="true">
          <el-tab-pane>
            <span style="font-family: 'PingFang SC';font-size: 16pt;padding: 0px" slot="label">登录</span>
            <el-form ref="loginForm" v-model="loginForm" style="margin-top: 15px">
              <el-form-item>
                <el-input placeholder="用户名" prefix-icon="el-icon-user-solid" v-model="loginForm.username"></el-input>
              </el-form-item>
              <el-form-item>
                <el-input placeholder="密码" prefix-icon="el-icon-lock" show-password
                          v-model="loginForm.password"></el-input>
              </el-form-item>
              <el-form-item>
                <el-checkbox style="float: left">记住我</el-checkbox>
                <el-link href="" :underline="false" style="float: right;">找回密码</el-link>
              </el-form-item>
              <el-form-item style="text-align: center">
                <el-button round type="primary" @click="submit" style="width: 100%;">提交</el-button>
              </el-form-item>
              <el-divider></el-divider>
              <span
                style="font-family: 'PingFang SC';text-align: center;display:block;font-size: 12px">大为远达科技发展有限公司</span>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </el-card>
    </el-main>
    <el-footer style="background: #416bbd;text-align: center;vertical-align: middle">
      <span style="color: #ffffff;font-size: 18px;font-family:cursive;display: block;margin-top: 20px">关于大为远达 | 大为产品简介 | 联系我们 | 帮助中心</span>
    </el-footer>
  </el-container>
</template>
<script>
// import elementUiUtil from '../../util/elementui-util'
export default {
  data () {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      topLogoUrl: '../../static/image/logo.png',
      mainLogoUrl: '../../static/image/mainlogo.png'
    }
  },
  methods: {
    submit: function () {
      this.axios.post('/token', {username: this.loginForm.username, password: this.loginForm.password}).then(res => {
        // 存入token
        localStorage.setItem('token', res.data)
        this.$router.push({path: '/dashboard'})
      }).catch()
    }
  }
}
</script>
<style>
  .el-form-item {
    padding: 0px;
  }
</style>
