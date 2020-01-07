<template>
  <el-card class="box-card">
    <el-table
      :data="applyTaskTableData"
      style="width: 100%">
      <el-table-column
        label="申请日期"
        width="220">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.applyCreateTime }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="审批人姓名"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.applyName }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="标签"
        width="160">
        <template slot-scope="scope">
          <el-tag>
            {{formatTag(scope.row)}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="状态"
        width="160"
        :filters="[{ text: '尚未处理', value: 0 }, { text: '已处理', value:1 },{text:'退回拒绝',value:2}]"
        :filter-method="filterTag">
        <template slot-scope="scope">
          <el-tag :type="formatStatusType(scope.row)">
            {{formatStatus(scope.row)}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="loadApplyForm(scope.row.id,scope.row.suggest,scope.row.applyId,scope.row.applyType)">查看详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="dialogVisible" center width="40%">
      <el-form :model="applyForm" label-width="100px" label-position="left" :disabled="true">
        <el-row>
          <el-col :span="20" :offset="2">
            <el-form-item label="工号">
              <el-input v-model="applyForm.username"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="20" :offset="2">
            <el-form-item label="姓名">
              <el-input v-model="applyForm.name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="applyForm.applyType === 0 ">
          <el-col :span="20" :offset="2">
            <el-form-item label="请假类型">
              <el-input :value="formatHolidayType(applyForm.holidayType)"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="applyForm.startTime != null">
          <el-col :span="20" :offset="2">
            <el-form-item label="请假时间">
              <el-input :value="formatHolidayTime(applyForm.startTime,applyForm.endTime)"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="applyForm.applyType === 3">
          <el-col :span="20" :offset="2">
            <el-form-item label="补卡日期 - 种类">
              <el-input :value="formatDateTime(applyForm.dateTime,applyForm.missType)"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="applyForm.applyType === 4">
          <el-col :span="20" :offset="2">
            <el-form-item label="补假日期">
              <el-input :value="applyForm.dateTime"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="applyForm.tripSite!=null">
          <el-col :span="20" :offset="2">
            <el-form-item label="地点">
              <el-input :value="applyForm.tripSite"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="20" :offset="2">
            <el-form-item label="理由" prop="applyReason">
              <el-input
                v-model="applyForm.applyReason"
                type="textarea"
                :rows="4">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="applyForm.applyType<=2">
          <el-col :span="20" :offset="2">
            <el-form-item label="附件" prop="badHistoryId">
              <el-link v-for="item in this.files" v-bind:key="item.id" style="display: inline" :underline="false"
                       :href="download(item.id,item.name)" @click="download(item.id,item.name)">
                {{item.name}}
              </el-link>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="20" :offset="2">
            <el-form-item label="意见">
              <el-input v-model="suggest"
                        type="textarea"
                        :rows="5">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </el-card>
</template>
<script>
// import elementUiUtil from '../../util/elementui-util'

export default {
  data () {
    return {
      dialogVisible: false,
      applyTaskTableData: [],
      applyRemakeForm: [],
      stepsActive: '',
      applyForm: {filePath: ''},
      files: [],
      suggest: ''
    }
  },
  created: function () {
    this.loadApplyTaskTableData()
  },
  methods: {
    loadApplyTaskTableData () {
      this.axios.get('/applyTask/user').then(result => {
        this.applyTaskTableData = result.data
      }).catch()
    },
    loadApplyForm (id, suggest, applyId, applyType) {
      this.dialogVisible = true
      this.axios.get('applyTask/apply', {
        taskId: id,
        suggest: suggest,
        applyId: applyId,
        applyType: applyType
      }).then(result => {
        this.applyForm = result.data
        this.suggest = this.applyForm.suggest
        this.files = []
        if (this.applyForm.filePath !== '' && this.applyForm.filePath != null) {
          let files = this.applyForm.filePath.split(',')
          files = files !== null ? files : this.applyForm.filePath
          files.forEach(file => {
            let tempArray = file.split('\\')
            this.files.push({'id': file, 'name': tempArray[tempArray.length - 1]})
          })
        }
      }).catch()
    },
    formatHolidayType (holidayType) {
      switch (holidayType) {
        case 0:
          return '事假'
        case 1:
          return '病假'
        case 2:
          return '年假'
        case 3:
          return '婚嫁'
        case 4:
          return '产假'
        case 5:
          return '陪产假'
      }
    },
    formatHolidayTime (startTime, endTime) {
      return startTime + ' - ' + endTime
    },
    formatTag (row) {
      switch (row.applyType) {
        case 0:
          return '请假申请'
        case 1:
          return '外勤申请'
        case 2:
          return '出差申请'
        case 3:
          return '补卡申请'
        case 4:
          return '补假申请'
      }
    },
    formatStatus (row) {
      switch (row.status) {
        case 0:
          return '暂未处理'
        case 1:
          return '审批通过'
        case 2:
          return '申请被拒'
      }
    },
    formatStatusType (row) {
      switch (row.status) {
        case 0:
          return 'info'
        case 1:
          return 'success'
        case 2:
          return 'danger'
      }
    },
    formatDateTime (dateTime, missType) {
      return dateTime + ' - ' + (missType === 0 ? '上班卡' : '下班卡')
    },
    handleDelete (row, index) {
      this.removeMessageTipRowData(row.id)
      this.messageTipTableData.splice(index, 1)
    },
    download (filePath, fileName) {
      return '/api/file?fileName=' + encodeURIComponent(fileName) + '&filePath=' + encodeURIComponent(filePath)
    },
    filterTag (value, row) {
      return row.status === value
    }
  }
}
</script>
<style rel="stylesheet/scss" xml:lang="scss">
  .el-card__body {
    height: 100%;
    padding: 5%;
  }

  .el-checkbox-group {
    height: 100px;
  }
</style>
<style rel="stylesheet/scss" xml:lang="scss" scoped>
  .box-card {
    width: 80%;
    height: 100%;
    padding-left: 10%;
    padding-right: 10%;
  }
</style>
