<template>
  <el-card class="box-card">
    <el-steps :space="230" :active="stepsActive" process-status="process" finish-status="success">
      <el-step title="步骤1:填写请假申请表" icon="el-icon-edit"></el-step>
      <el-step title="步骤2:等待审核" icon="el-icon-message"></el-step>
      <el-step title="步骤3:审核通过" icon="el-icon-check"></el-step>
    </el-steps>
    <el-form :model="applyLeaveForm" ref="applyLeaveForm" label-width="100px" label-position="left" :rules="rules">
      <el-row>
        <el-form-item label="请假类型" required>
          <el-select class="" v-model="applyLeaveForm.holidayType" placeholder="请选择请假类型" style="width: 380px;">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="请假时间" required>
          <el-date-picker
            style="width: 380px"
            v-model="dateTimeRange"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="请假事由" prop="applyReason">
            <el-input
              v-model="applyLeaveForm.applyReason"
              type="textarea"
              :rows="4"
              placeholder="请输入内容"
            >
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="附件">
            <el-upload
              class="upload-demo"
              :action="uploadUrl()"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-upload="beforeUpload"
              multiple
              :limit="5"
              :on-exceed="handleExceed"
              :on-success="handleSuccess"
              :file-list="fileList">
              <el-button type="primary">点击上传<i class="el-icon-upload el-icon--right"></i></el-button>
              <div slot="tip" class="el-upload__tip">每个上传文件不超过1MB</div>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="审批人" prop="applyUsername">
            <el-input v-model="applyLeaveForm.applyName" readonly>
              <el-popover
                placement="right"
                width="400"
                trigger="click"
                slot="append">
                <el-tree :data="userTreeData" :props="defaultProps" @check-change="handleNodeChange"
                         @node-click="handleNodeClick" ref="tree"
                         node-key="username" check-strictly
                         check-on-click-node
                         show-checkbox></el-tree>
                <el-button slot="reference">人员选择</el-button>
              </el-popover>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-form-item>
          <el-button type="primary" @click="submitForm('applyLeaveForm')">提交申请</el-button>
        </el-form-item>
      </el-row>
    </el-form>
  </el-card>
</template>
<script>
import elementUiUtil from '../../util/elementui-util'

export default {
  data () {
    return {
      stepsActive: 0,
      rules: {
        applyReason: {
          required: true,
          message: '请假事由不能为空'
        },
        applyName: {
          required: true,
          message: '审批人不能为空'
        }
      },
      applyLeaveForm: {applyName: '', filePath: ''},
      fileList: [],
      dateTimeRange: [],
      options:
        [{
          value: 0,
          label: '事假'
        }, {
          value: 1,
          label: '病假'
        }, {
          value: 2,
          label: '年假'
        }, {
          value: 3,
          label: '婚假'
        }, {
          value: 4,
          label: '产假'
        }, {
          value: 5,
          label: '陪产假'
        }],
      userTreeData: [],
      defaultProps:
        {
          label: 'name',
          children:
            'children'
        }
    }
  },
  created: function () {
    this.loadUserTreeData()
  },
  methods: {
    loadUserTreeData () {
      this.axios.get('/userDetail/tree').then(result => {
        this.userTreeData = result.data
      }).catch()
    },
    handleRemove (file, fileList) {
      console.log(file, fileList)
    },
    handlePreview (file) {
      console.log(file)
    },
    handleExceed (files, fileList) {
      this.$message.warning('限制选择3个文件且每个文件大小不超过1MB')
    },
    beforeUpload (file) {
      let fileSize = file.size / 1024 / 1024 < 2
      if (!fileSize) {
        this.$message({
          message: '上传文件大小不能超过1MB!',
          type: 'warning'
        })
        return false
      }
    },
    handleSuccess (response) {
      this.applyLeaveForm.filePath === '' ? this.applyLeaveForm.filePath += response.data : this.applyLeaveForm.filePath += ',' + response.data
    },
    uploadUrl () {
      return 'api/file'
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let startTime = this.dateTimeRange[0]
          let endTime = this.dateTimeRange[1]
          this.$set(this.applyLeaveForm, 'startTime', startTime)
          this.$set(this.applyLeaveForm, 'endTime', endTime)
          this.applyLeaveForm.applyType = 0
          this.applyLeaveForm.applyTime = Date.parse(endTime) - Date.parse(startTime)
          this.applyLeaveForm.applyStatus = 0
          this.axios.post('/applyLeave', this.applyLeaveForm).then(result => {
            elementUiUtil.Message(result.message, 'success')
            this.stepsActive = 1
          }).catch(err => {
            elementUiUtil.Message(err.message, 'error')
          })
        } else {
          return false
        }
      })
    },
    handleNodeChange (data, checked) {
      if (checked) {
        this.applyLeaveForm.applyUsername = data.username
        this.applyLeaveForm.applyName = data.name
        this.$refs.tree.setCheckedKeys([data.username])
      }
    },
    handleNodeClick (data) {
      if (!data.disabled) {
        this.applyLeaveForm.applyUsername = data.username
        this.applyLeaveForm.applyName = data.name
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

  .el-steps {
    margin-bottom: 30px;
  }
</style>
