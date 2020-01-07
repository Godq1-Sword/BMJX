<template>
  <el-card class="box-card">
    <el-table
      :data="messageTipTableData"
      style="width: 80%">
      <el-table-column
        label="日期"
        width="180">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.dateTime }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="标签"
        width="160">
        <template slot-scope="scope">
          <el-tag>
            {{renderTag(scope.row)}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="内容"
        width="260">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.content }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row,scope.$index)">删除
          </el-button>
          <el-button
            v-if="handleBtnCtl(scope.row)"
            size="mini"
            type="primary"
            @click="handleBtnCli(scope.row)">{{handleBtnTxt(scope.row)}}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 20px">
      <el-button @click="removeMessageTipAll()">全部删除</el-button>
    </div>
  </el-card>
</template>
<script>
import elementUiUtil from '../../util/elementui-util'

export default {
  data () {
    return {
      messageTipTableData: []
    }
  },
  created: function () {
    this.loadMessageTipTableData()
  },
  methods: {
    loadMessageTipTableData () {
      this.axios.get('/messageTip/user', {username: 'admin'}).then(result => {
        this.messageTipTableData = result.data
      }).catch()
    },
    removeMessageTipRowData (id) {
      this.axios.del('/messageTip/id', {id: id}).then(result => {
        elementUiUtil.Message(result.message, 'success')
      }).catch()
    },
    removeMessageTipAll () {
      this.$confirm('是否删除全部消息?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios.del('/messageTip/user').then(result => {
          elementUiUtil.Message((result.message, 'success'))
        }).catch()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    renderTag (row) {
      switch (row.behaviorType) {
        case 0:
          return '迟到提醒'
        case 1:
          return '早退提醒'
        case 2:
          return row.missType === 0 ? '上班卡未打' : '下班卡未打'
        case 3:
          return '旷工提醒'
      }
    },
    handleEdit (index, row) {
      console.log(index, row)
    },
    handleDelete (row, index) {
      this.removeMessageTipRowData(row.id)
      this.messageTipTableData.splice(index, 1)
    },
    handleBtnCli (row) {
      row.behaviorType === 2 ? this.$router.push({
        name: 'ApplyForBackCard',
        params: {badHistoryId: row.badHistoryId}
      }) : this.$router.push({name: 'ApplyForBackHoliday', params: {badHistoryId: row.badHistoryId}})
    },
    handleBtnCtl (row) {
      let bool = Math.abs(new Date().getTime() - new Date(row.dateTime + ' 00:00:00').getTime()) / (1000 * 3600 * 24) < 7
      return bool && (row.behaviorType === 2 || row.behaviorType === 3)
    },
    handleBtnTxt (row) {
      return row.behaviorType === 2 ? '补卡' : '补假'
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

  .el-steps {
    margin-bottom: 30px;
  }
</style>
