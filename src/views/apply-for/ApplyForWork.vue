<template>
  <el-card class="box-card">
    <el-steps :space="230" :active="stepsActive" process-status="process" finish-status="success">
      <el-step title="步骤1:填写外勤申请表" icon="el-icon-edit"></el-step>
      <el-step title="步骤2:等待审核" icon="el-icon-message"></el-step>
      <el-step title="步骤3:审核通过" icon="el-icon-check"></el-step>
    </el-steps>
    <el-form :model="applyLeaveForm" ref="applyLeaveForm" label-width="100px" label-position="left" :rules="rules">
      <el-row>
        <el-form-item label="外勤地点" required>
          <el-cascader
            style="width: 380px"
            size="large"
            :options="options"
            v-model="tripSite">
          </el-cascader>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="外勤时间" required>
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
          <el-form-item label="任务内容" prop="applyReason">
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
                <el-tree :data="treeData" :props="defaultProps" @check-change="handleNodeChange"
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
import {
  regionData,
  CodeToText
} from 'element-china-area-data'

export default {
  data () {
    return {
      options: regionData,
      tripSite: [],
      stepsActive: 0,
      rules: {
        username: {
          required: true,
          message: '工号不能为空,由字母数字下划线组成',
          pattern: /^\w+$/
        },
        name: {
          required: true,
          message: '姓名不能为空,由汉字或字母组成',
          pattern: /^[\u4e00-\u9fa5a-zA-Z]+$/
        },
        applyReason: {
          required: true,
          message: '任务内容不能为空'
        },
        applyUsername: {
          required: true,
          message: '审批人不能为空'
        }
      },
      applyLeaveForm: {applyName: '', filePath: ''},
      fileList: [],
      dateTimeRange: [],
      treeData: [],
      defaultProps:
        {
          label: 'name',
          children:
            'children'
        }
    }
  },
  created: function () {
    this.axios.get('/userDetail/tree').then(result => {
      this.treeData = result.data
    }).catch()
  },
  methods: {
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
          this.$set(this.applyLeaveForm, 'tripSite', CodeToText[this.tripSite[0]] + CodeToText[this.tripSite[1]] + CodeToText[this.tripSite[2]])
          this.applyLeaveForm.applyType = 1
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
        this.applyLeaveForm.applyUsername = data.name
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
