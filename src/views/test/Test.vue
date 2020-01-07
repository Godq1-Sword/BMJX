<template>
  <el-card class="box-card">
    <el-row>
      <el-button @click="handleClick">生成测试数据</el-button>
    </el-row>
  </el-card>
</template>
<script>

export default {
  data () {
    return {}
  },
  methods: {
    sleep () {
      // 暂停5s
      var start = (new Date()).getTime()
      while ((new Date()).getTime() - start < 5000) {
        continue
      }
    },
    async handleClick () {
      let month = '12'
      let day = 1
      let inHour = '07'
      let second = 45
      let outHour = '16'
      for (; day <= 31; day++, second++) {
        if (!((day >= 4 && day <= 8) || (day >= 17 && day <= 18) || (day >= 25 && day <= 26) || day === 6 || day === 30 || day === 1 ||
          day === 14 || day === 15 || day === 21 || day === 22 || day === 28 || day === 29)) {
          let dayStr = day < 10 ? '0' + day : day
          if (second >= 60) {
            inHour = '08'
            outHour = '17'
            second = second >= 60 ? second - 60 : second
          }
          let secondStr = second < 10 ? '0' + second : second
          let inClockTime = '2019-' + month + '-' + dayStr + ' ' + inHour + ':' + secondStr + ':00'
          let outClockTime = '2019-' + month + '-' + dayStr + ' ' + outHour + ':' + secondStr + ':00'
          console.log(inClockTime + ' ' + outClockTime)
          // 上班
          await this.axios.post('/clockHistory', {username: 'admin', clockTime: inClockTime}).then(result => {
          }).catch()
          // 下班
          await this.axios.post('/clockHistory', {username: 'admin', clockTime: outClockTime}).then(result => {
          }).catch()
          this.sleep(2000)
        }
      }
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
    width: 80%;
    height: 100%;
    padding-left: 20%;
  }
</style>
