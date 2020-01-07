<template>
  <el-card class="box-card">
    <el-steps :space="230" :active="stepsActive" process-status="process" finish-status="success">
      <el-step title="步骤1:填写补假申请表" icon="el-icon-edit"></el-step>
      <el-step title="步骤2:等待审核" icon="el-icon-message"></el-step>
      <el-step title="步骤3:审核通过" icon="el-icon-check"></el-step>
    </el-steps>
    <el-form :model="applyRemakeForm" ref="applyRemakeForm" label-width="100px" label-position="left" :rules="rules">
      <el-row>
        <el-col :span="12">
          <el-form-item label="补假日期" prop="badHistoryId">
            <el-input v-model="badHistoryText" readonly>
              <el-popover
                placement="right"
                width="400"
                trigger="click"
                slot="append">
                <el-table
                  ref="multipleTable"
                  :data="badHistroyTableData"
                  tooltip-effect="dark"
                  style="width: 100%"
                  @selection-change="handleSelectionChange">
                  <el-table-column
                    type="selection">
                  </el-table-column>
                  <el-table-column
                    label="日期">
                    <template slot-scope="scope">{{ scope.row.dateTime }}</template>
                  </el-table-column>
                </el-table>
                <el-button slot="reference">选择记录</el-button>
              </el-popover>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="补假理由" prop="applyReason">
            <el-input
              v-model="applyRemakeForm.applyReason"
              type="textarea"
              :rows="4"
              placeholder="请输入内容">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="审批人" prop="applyUsername">
            <el-input v-model="applyRemakeForm.applyUsername" readonly>
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
          <el-button type="primary" @click="submitForm('applyRemakeForm')">提交申请</el-button>
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
      badHistoryText: '',
      badHistroyTableData: [],
      stepsActive: 0,
      rules: {
        badHistoryId: {
          required: true,
          message: '补假日期未选'
        },
        applyReason: {
          required: true,
          message: '补假原因不能为空'
        },
        applyUsername: {
          required: true,
          message: '审批人不能为空'
        }
      },
      applyRemakeForm: {applyUsername: '', badHistoryId: ''},
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
    this.loadBadHistroyTableData(7)
    this.loadUserTreeData()
  },
  methods: {
    loadBadHistroyTableData (historyDays) {
      this.axios.get('/badHistory', {username: 'admin', historyDays: historyDays}).then(result => {
        result.data.forEach(tableData => {
          if (tableData.behaviorType === 3) {
            this.badHistroyTableData.push(tableData)
          }
        })
      }).catch()
    },
    loadUserTreeData () {
      this.axios.get('/userDetail/tree', {username: 'admin'}).then(result => {
        this.userTreeData = result.data
      }).catch()
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.applyRemakeForm.applyType = 4
          this.applyRemakeForm.applyStatus = 0
          this.axios.post('/applyRemake', this.applyRemakeForm).then(result => {
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
        this.applyRemakeForm.applyUsername = data.name
        this.$refs.tree.setCheckedKeys([data.username])
      }
    },
    handleNodeClick (data) {
      if (!data.disabled) {
        this.applyRemakeForm.applyUsername = data.name
      }
    },
    handleSelectionChange (val) {
      this.badHistoryText = ''
      this.applyRemakeForm.badHistoryId = ''
      for (let i = 0; i < val.length; i++) {
        let split = i !== val.length - 1 ? ',' : ''
        this.applyRemakeForm.badHistoryId += val[i].id + split
        this.badHistoryText += val[i].dateTime + split
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
