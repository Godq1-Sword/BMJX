<!--侧边菜单栏-->
<template>
  <div style="margin-top: 10%;height: 100%;">
    <div style="text-align: center;margin-bottom: 5%;">
      <el-image :src="logoUrl" style=""></el-image>
      <div style="margin-top: 10px;text-align: center">
        <span style="font-size: 20px;font-weight:bold;color: #416bbd;display: block">大 为 科 技</span>
        <span style="font-size: 12px;color: #416bbd"><br>DaWei Technology</span>
      </div>
    </div>
    <el-col :span="24">
      <el-menu
        background-color="#ffffff"
        active-text-color="#416bbd"
        style="border: none"
        @open="handleOpen"
        router>
        <el-submenu index="1">
          <template slot="title"><i class="el-icon-s-management"></i>申报申请</template>
          <el-menu-item index="/applyForLeave">请假</el-menu-item>
          <el-menu-item index="/applyForWork">外勤</el-menu-item>
          <el-menu-item index="/applyForBusiness">出差</el-menu-item>
          <el-menu-item index="/applyForBackCard">补卡</el-menu-item>
          <el-menu-item index="/applyForBackHoliday">补假</el-menu-item>
        </el-submenu>
        <el-submenu index="2">
          <template slot="title"><i class="el-icon-s-claim"></i>考勤信息</template>
          <el-menu-item index="/personal">我的考勤信息</el-menu-item>
          <el-menu-item index="/calendar">考勤月历</el-menu-item>
          <el-menu-item index="/excelExport">考勤报表</el-menu-item>
        </el-submenu>
        <el-submenu index="3">
          <template slot="title"><i class="el-icon-user-solid"></i>个人中心</template>
          <el-menu-item index="/userInfo">个人信息</el-menu-item>
          <el-menu-item index="/messageTip">
            <el-badge :value="messageAmount" class="item" type="primary">
              消息提醒
            </el-badge>
          </el-menu-item>
          <el-submenu index="3-3">
            <template slot="title">我的工作台</template>
            <el-menu-item index="/applyTaskList">待办事宜</el-menu-item>
            <el-menu-item index="/test">生成测试数据</el-menu-item>
            <el-menu-item index="/messagePro">申请进展</el-menu-item>
          </el-submenu>
        </el-submenu>
      </el-menu>
    </el-col>
  </div>
</template>

<script>
export default {
  name: 'Sidebar',
  data () {
    return {
      logoUrl: '../../../static/image/logo.png',
      messageAmount: ''
    }
  },
  methods: {
    handleOpen (key) {
      if (key === '3') {
        this.getMessageAmount()
      }
    },
    getMessageAmount () {
      this.axios.get('/messageTip/amount').then(result => {
        this.messageAmount = result.data
      }).catch()
    }
  }
}
</script>

<style rel="stylesheet/scss" xml:lang="scss" scoped>
  .el-menu-vertical-demo {
    border: none;
  }
</style>
