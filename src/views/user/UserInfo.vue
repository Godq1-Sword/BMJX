<template>
  <el-card class="box-card">
    <el-form :model="userDetailForm" ref="userDetailForm" label-width="100px" label-position="left">
      <el-row>
        <el-col :span="12">
          <el-form-item label="用户名">
            <el-input v-model="userDetailForm.username" disabled></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="姓名">
            <el-input v-model="userDetailForm.name"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-form-item>
          <el-button type="primary" @click="submitForm('userDetailForm')">修改</el-button>
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
      userDetailForm: {}
    }
  },
  created: function () {
    this.loadUserDetail()
  },
  methods: {
    loadUserDetail () {
      this.axios.get('/userDetail/user').then(result => {
        this.userDetailForm = result.data
      }).catch()
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.put('/userDetail/user', {username: this.userDetailForm.username, name: this.userDetailForm.name}
          ).then(result => {
            elementUiUtil.Message(result.message, 'success')
          }).catch(err => {
            elementUiUtil.Message(err.message, 'error')
          })
        } else {
          return false
        }
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
    padding-left: 20%;
  }
</style>
