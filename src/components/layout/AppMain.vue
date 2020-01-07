<!--# 页面容器-->
<template>
  <section class="app-main">
    <router-view></router-view>
  </section>
</template>

<script>
export default {
  name: 'AppMain',
  data () {
    return {}
  }
}
</script>
<style rel="stylesheet/scss" xml:lang="scss" scoped>
  .app-main {
    height: 100%;
    width: 100%;
  }
</style>
