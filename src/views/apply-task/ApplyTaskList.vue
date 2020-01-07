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
        label="申请人姓名"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.name }}</span>
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
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="loadApplyForm(scope.row.id,scope.row.suggest,scope.row.applyId,scope.row.applyType,scope.$index)">查看详情
          </el-button>
          <el-button
            size="mini"
            type="success"
            @click="quickApprove(scope.row,scope.$index,1)">快速审批通过
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="quickApprove(scope.row,scope.$index,2)">快速拒绝申请
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="dialogVisible" center width="40%">
      <el-form :model="applyForm" label-width="100px" label-position="left">
        <el-row>
          <el-col :span="20" :offset="2">
            <el-form-item label="工号">
              <el-input v-model="applyForm.username" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="20" :offset="2">
            <el-form-item label="姓名">
              <el-input v-model="applyForm.name" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="applyForm.applyType === 0 ">
          <el-col :span="20" :offset="2">
            <el-form-item label="请假类型">
              <el-input :disabled="true" :value="formatHolidayType(applyForm.holidayType)"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="applyForm.startTime != null">
          <el-col :span="20" :offset="2">
            <el-form-item label="请假时间">
              <el-input :disabled="true" :value="formatHolidayTime(applyForm.startTime,applyForm.endTime)"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="applyForm.applyType === 3">
          <el-col :span="20" :offset="2">
            <el-form-item label="补卡日期 - 种类">
              <el-input :disabled="true" :value="formatDateTime(applyForm.dateTime,applyForm.missType)"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="applyForm.applyType === 4">
          <el-col :span="20" :offset="2">
            <el-form-item label="补假日期">
              <el-input :disabled="true" :value="applyForm.dateTime"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="applyForm.tripSite!=null">
          <el-col :span="20" :offset="2">
            <el-form-item label="地点">
              <el-input :disabled="true" :value="applyForm.tripSite"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="20" :offset="2">
            <el-form-item label="理由" prop="applyReason">
              <el-input
                v-model="applyForm.applyReason"
                type="textarea"
                :rows="4"
                :disabled="true">
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
      <div slot="footer" class="dialog-footer">
        <el-button @click="approve" type="success">批 准</el-button>
        <el-button type="danger" @click="sendBack">退 回</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>
<script>
// import elementUiUtil from '../../util/elementui-util'

export default {
  data () {
    return {
      rowIndex: '',
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
      this.axios.get('/applyTask/applyUser').then(result => {
        this.applyTaskTableData = result.data
      }).catch()
    },
    loadApplyForm (id, suggest, applyId, applyType, index) {
      this.rowIndex = index
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
    formatDateTime (dateTime, missType) {
      return dateTime + ' - ' + (missType === 0 ? '上班卡' : '下班卡')
    },
    download (filePath, fileName) {
      return '/api/file?fileName=' + encodeURIComponent(fileName) + '&filePath=' + encodeURIComponent(filePath)
    },
    quickApprove (row, index, status) {
      this.$confirm('此操作将同意该申请, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios.put('applyTask', {taskId: row.id, status: status}).then(result => {
        }).catch()
        this.$message({
          type: 'success',
          message: '操作成功!'
        })
        this.dialogVisible = false
        this.applyTaskTableData.splice(index, 1)
      }).catch(() => {
      })
    },
    approve () {
      this.$confirm('此操作将同意该申请, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios.put('applyTask', {
          taskId: this.applyForm.taskId,
          status: 1,
          suggest: this.suggest
        }).then(result => {
        }).catch()
        this.$message({
          type: 'success',
          message: '操作成功!'
        })
        this.dialogVisible = false
        this.applyTaskTableData.splice(this.rowIndex, 1)
      }).catch(() => {
      })
    },
    sendBack () {
      this.$confirm('此操作将退回该申请, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios.put('applyTask', {
          taskId: this.applyForm.taskId,
          status: 2,
          suggest: this.suggest
        }).then(result => {
        }).catch()
        this.$message({
          type: 'success',
          message: '操作成功!'
        })
        this.dialogVisible = false
        this.applyTaskTableData.splice(this.rowIndex, 1)
      }).catch(() => {
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
    width: 80%;
    height: 100%;
    padding-left: 10%;
    padding-right: 10%;
  }

  .el-steps {
    margin-bottom: 30px;
  }

</style>
