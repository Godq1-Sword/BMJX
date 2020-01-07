<template>
  <el-card class="box-card">
    <el-switch
      v-model="monthOrDateFlag"
      active-color="#13ce66"
      inactive-color="#ff4949"
      active-text="月历"
      inactive-text="周历"
      @change="switchChange">
    </el-switch>
    <div id="div_overview">
      <span id="span_personal">个人考勤信息</span>
      <el-progress type="circle" :percentage="percentAge" :stroke-width="10" :width="200"></el-progress>
      <el-divider content-position="center">平均工时</el-divider>
      <span class="span_overview">{{avgWorkTime}}</span>
      <el-divider content-position="center">请假情况</el-divider>
      <span class="span_apply_content" v-html="applyContent"></span>
    </div>
    <div class="div_block">
      <div class="div_block_title">
        <span class="span_block_title">迟&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;到</span>
      </div>
      <div class="div_block_content">
        <template>
          <el-table
            :data="lateTableData"
            style="width: 99%;border-radius: 10px"
            max-height="145">
            <el-table-column v-if="lateTableData!==null">
              <span slot="header" slot-scope="scope">迟到 - {{lateTableData.length}}次</span>
              <template slot-scope="scope">
                <span>{{scope.row.dateTime}}</span>
              </template>
            </el-table-column>
            <el-table-column>
              <span slot="header" slot-scope="scope">累计 - {{formatTime(lateTotalTime)}}</span>
              <template slot-scope="scope">
                <span>打卡时间 - {{formatClockTime(scope.row.clockTime)}}</span>
              </template>
            </el-table-column>
            <el-table-column>
              <template slot-scope="scope">
                <span>迟到 - {{formatTime(scope.row.overTime)}}</span>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </div>
    </div>
    <div class="div_block">
      <div class="div_block_title">
        <span class="span_block_title">早&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;退</span>
      </div>
      <div class="div_block_content">
        <template>
          <el-table
            :data="earlyTableData"
            style="width: 99%;border-radius: 10px" max-height="145">
            <el-table-column>
              <span slot="header" slot-scope="scope">早退 - {{earlyTableData.length}}次</span>
              <template slot-scope="scope">
                <span>{{scope.row.dateTime}}</span>
              </template>
            </el-table-column>
            <el-table-column>
              <span slot="header" slot-scope="scope">累计 - {{formatTime(earlyTotalTime)}}</span>
              <template slot-scope="scope">
                <span>打卡时间 - {{formatClockTime(scope.row.clockTime)}}</span>
              </template>
            </el-table-column>
            <el-table-column>
              <template slot-scope="scope">
                <span>早退 - {{formatTime(scope.row.overTime)}}</span>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </div>
    </div>

    <div class="div_block">
      <div class="div_block_title">
        <span class="span_block_title">缺&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;卡</span>
      </div>
      <div class="div_block_content">
        <template>
          <el-table
            :data="missTableData"
            style="width: 99%;border-radius: 10px" max-height="145">
            <el-table-column>
              <span slot="header" slot-scope="scope">缺卡 - {{missTableData.length}}次</span>
              <template slot-scope="scope">
                <span>{{scope.row.dateTime}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="missType" :formatter="formatMissType">
            </el-table-column>
            <el-table-column prop="backType" :formatter="formatBackType">
            </el-table-column>
          </el-table>
        </template>
      </div>
    </div>

    <div class="div_block">
      <div class="div_block_title">
        <span class="span_block_title">旷&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工</span>
      </div>
      <div class="div_block_content">
        <template>
          <el-table
            :data="absentTableData"
            style="width: 99%;border-radius: 10px"
            max-height="145">
            <el-table-column>
              <span slot="header" slot-scope="scope">矿工 - {{absentTableData.length}}次</span>
              <template slot-scope="scope">
                <span>{{scope.row.dateTime}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="backType" :formatter="formatBackType">
            </el-table-column>
          </el-table>
        </template>
      </div>
    </div>
  </el-card>
</template>
<script>
export default {
  data () {
    return {
      lateTableData: [],
      earlyTableData: [],
      missTableData: [],
      absentTableData: [],
      lateTotalTime: '',
      earlyTotalTime: '',
      monthOrDateFlag: false,
      loading: true,
      percentAge: 0,
      avgWorkTime: '',
      applyContent: ''
    }
  },
  created: function () {
    this.loadData(7)
  },
  methods: {
    loadData (historyDays) {
      this.axios.get('/badHistory', {historyDays: historyDays}).then(result => {
        let tableData = result.data
        this.lateTableData = []
        this.earlyTableData = []
        this.missTableData = []
        this.absentTableData = []
        let lateTotalTime = 0
        let earlyTotalTime = 0
        for (let i = 0; i < tableData.length; i++) {
          switch (tableData[i].behaviorType) {
            case 0:
              this.lateTableData.push(tableData[i])
              lateTotalTime += tableData[i].overTime
              break
            case 1:
              this.earlyTableData.push(tableData[i])
              earlyTotalTime += tableData[i].overTime
              break
            case 2:
              this.missTableData.push(tableData[i])
              break
            case 3:
              this.absentTableData.push(tableData[i])
              break
          }
        }
        this.lateTotalTime = lateTotalTime
        this.earlyTotalTime = earlyTotalTime
      }).catch()
      this.axios.get('/clockHistory/user/workTime', {historyDays: historyDays}).then(result => {
        if (result.data != null) {
          this.percentAge = result.data.percentAge * 100
          this.avgWorkTime = result.data.avgWorkTime
        }
      }).catch()
      this.axios.get('/applyLeave/user', {historyDays: historyDays}).then(result => {
        this.applyContent = ''
        if (result.data != null && result.data.length > 0) {
          result.data.forEach(data => {
            this.applyContent += '开始日期: ' + data.startTime.substr(2, 14) + '<br>'
            this.applyContent += '结束日期: ' + data.endTime.substr(2, 14) + '<br><br>'
          })
        }
      }).catch()
    },
    formatClockTime (clockTime) {
      return clockTime.substr(11, 5)
    },
    formatTime (time) {
      var hours = parseInt(time / 3600000)
      var minutes = parseInt((time - hours * 3600000) / (60000))
      return hours + '时' + minutes + '分钟'
    },
    formatMissType (row) {
      return row.missType === 0 ? '上班卡' : '下班卡'
    },
    formatBackType (row) {
      switch (row.backType) {
        case -1:
          return '无需补卡'
        case 0:
          return '未补卡'
        case 1:
          return '已补卡'
        case 2:
          return '未补假'
        case 3:
          return '已补假'
      }
    },
    switchChange () {
      this.monthOrDateFlag ? this.loadData(30) : this.loadData(7)
    }
  }
}
</script>
<style rel="stylesheet/scss" xml:lang="scss">
  .el-card__body {
    height: 100%;
  }
</style>
<style rel="stylesheet/scss" xml:lang="scss" scoped>
  .box-card {
    width: 100%;
    height: 100%;
  }

  #span_personal {
    font-size: 18px;
    font-weight: bold;
    color: #416bbd;
  }

  .el-switch {
    margin-right: 15%;
    display: block;
    float: right;
  }

  #div_overview {
    padding: 5%;
    width: 20%;
    height: 100%;
    text-align: center;
    float: left
  }

  .div_block {
    width: 60%;
    height: 145px;
    border: 2px solid #416bbd;
    border-radius: 15px;
    margin-top: 30px;
    float: left
  }

  .div_block_title {
    width: 30%;
    height: 100%;
    background: #416bbd;
    border-radius: 10px;
    text-align: center;
    border: none;
    float: left;
  }

  .span_block_title {
    font-size: 30px;
    color: #ffffff;
    margin-top: 45px;
    display: block;
  }

  .div_block_content {
    width: 60%;
    padding-left: 5%;
    padding-right: 5%;
    float: right;
  }

  .span_overview {
    font-size: 20px;
    font-weight: bold;
  }

  .span_apply_content {
    width: 100%;
    font-size: 16px;
    font-weight: bold;
  }

  .el-progress {
    margin-top: 10%;
  }
</style>
