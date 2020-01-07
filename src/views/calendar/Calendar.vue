<template>
  <el-card class="box-card">
    <el-calendar @click="handleCalendar(date,data)">
      <template
        slot="dateCell"
        slot-scope="{date, data}">
        <div @click="handleCalendar(date,data)" style="width: 100%;height: 100%">
          {{ data.day.split('-').slice(1).join('-') }}
        </div>
      </template>
    </el-calendar>
    <el-card style="margin-top: 10%">
      <span>
        日期 - {{date}}<br>
        上班打卡时间 - {{startTime}}<br>
        下班打卡时间 - {{endTime}}<br>
      </span>
      <el-tag
        v-for="tag in tags"
        :key="tag.name"
        :type="tag.type">
        {{tag.name}}
      </el-tag>
    </el-card>
  </el-card>
</template>
<script>
export default {
  data () {
    return {
      date: '未选择',
      startTime: '未选择',
      endTime: '未选择',
      tags: []
    }
  },
  methods: {
    handleCalendar (date, data) {
      this.tags = []
      this.date = data.day
      this.axios.get('/calendar/user', {dateTimeStr: data.day}).then(result => {
        this.startTime = result.data.startTime == null ? '未打卡' : result.data.startTime
        this.endTime = result.data.endTime == null ? '未打卡' : result.data.endTime
        if (result.data.behaviorTypes != null && result.data.behaviorTypes.length > 0) {
          result.data.behaviorTypes.forEach(behavior => {
            if (behavior !== 2) {
              let name = ''
              let type = ''
              switch (behavior) {
                case 0:
                  name = '迟到'
                  type = 'warning'
                  break
                case 1:
                  name = '早退'
                  type = 'warning'
                  break
                case 3:
                  name = '旷工'
                  type = 'danger'
              }
              this.tags.push({name: name, type: type})
            }
          })
        }
        if (result.data.missTypes != null && result.data.missTypes.length > 0) {
          result.data.missTypes.forEach(missType => {
            if (missType !== -1) {
              let name = ''
              let type = ''
              switch (missType) {
                case 0:
                  name = '缺上班卡'
                  type = 'warning'
                  break
                case 1:
                  name = '缺下班卡'
                  type = 'warning'
                  break
              }
              this.tags.push({name: name, type: type})
            }
          })
        }
        if (result.data.backTypes != null && result.data.backTypes.length > 0) {
          result.data.backTypes.forEach(backType => {
            if (backType !== -1) {
              let name = ''
              let type = ''
              switch (backType) {
                case 0:
                  name = '未补卡'
                  type = 'warning'
                  break
                case 1:
                  name = '补卡'
                  type = 'success'
                  break
                case 2:
                  name = '未补假'
                  type = 'warning'
                  break
                case 3:
                  name = '补假'
                  type = 'danger'
              }
              this.tags.push({name: name, type: type})
            }
          })
        }
        let applyType = result.data.applyType
        if (applyType != null && applyType !== '' && applyType !== -1) {
          let name = ''
          let type = ''
          switch (applyType) {
            case 0:
              name = '请假'
              type = 'warning'
              break
            case 1:
              name = '外勤'
              type = 'warning'
              break
            case 2:
              name = '出差'
              type = 'warning'
              break
          }
          this.tags.push({name: name, type: type})
        }
      }).catch(err => {
      })
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
    width: 100%;
    height: 100%;
  }

  .el-calendar {
    width: 50%;
    float: left;
  }

  .el-tag {
    margin-top: 5px;
    margin-right: 10px;
  }
</style>
