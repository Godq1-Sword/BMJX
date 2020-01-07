<!--模版框架-->
<template>
  <el-container>
    <el-header>
      <Navbar/>
    </el-header>
    <el-container style="width: 100%;height: 100%">
      <el-aside style="width: 200px;height: 100%">
        <el-scrollbar style="height: 100%">
          <Sidebar/>
        </el-scrollbar>
      </el-aside>
      <el-main>
        <AppMain/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
// 引入组件文件
import Sidebar from '@/components/layout/SideBar'
import Navbar from '@/components/layout/NavBar'
import AppMain from '@/components/layout/AppMain'

export default {
  name: 'dashboard',
  // 使用组件
  components: {
    Navbar,
    Sidebar,
    AppMain
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .el-header {
    padding: 0;
    margin-bottom: 1px;
    margin-left: 10%;
    margin-right: 10%;
  }

  .el-main {
    padding: 0;
    margin-right: 10%;
    overflow-x: hidden;
    overflow-y: hidden;
    border-right: 1px solid #ebeef5;
  }

  .el-aside {
    border-left: 1px solid #ebeef5;
    padding-top: 1%;
    margin-left: 10%
  }

  .el-scrollbar__wrap {
    overflow-x: hidden;
  }
</style>
