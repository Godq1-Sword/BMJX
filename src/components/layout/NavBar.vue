<!--顶部导航栏-->
<template>
  <el-menu class="el-menu-demo" mode="horizontal" background-color="#416bbd"
           text-color="#fff"
           active-text-color="#ffd04b"
           router>
    <!-- Logo -->
    <el-image :src="titleLogo" style="float: left;margin-left: 20px"></el-image>
    <el-dropdown trigger="click" @command="handleDropdown">
      <span class="el-dropdown-link">
        欢迎 - {{name}}<i class="el-icon-arrow-down el-icon--right"></i>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item icon="el-icon-setting" command="setting">个人信息设置</el-dropdown-item>
        <el-dropdown-item icon="el-icon-close" command="logining">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </el-menu>
</template>
<script>
export default {
  name: 'navbar',
  data () {
    return {
      name: '',
      activeName: 'second',
      titleLogo: '../../../static/image/titlelogo.jpg'
    }
  },
  created: function () {
    this.loadName()
  },
  methods: {
    loadName () {
      this.axios.get('/userDetail/user').then(result => {
        this.name = result.data.name
      }).catch()
    },
    handleDropdown (command) {
      if (command === 'setting') {
        this.$router.push({name: 'UserInfo'})
      } else {
        localStorage.removeItem('token')
        this.$router.push({name: 'Login'})
      }
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
  .el-special-menu-item:hover {
    background: #416bbd;
  }

  .el-dropdown {
    color: #ffffff;
    margin-top: 20px;
    margin-right: 15px;
    float: right;
  }
</style>
