<template>
  <div>
<!--    头部-->
    <Header/>
  </div>
<!--  主体-->
  <div style="display: flex">
    <Aside/>
<!--    内容区 从HomeView组件  路由机制-->
    <router-view style="flex: 1"/>
  </div>
</template>
<style>
</style>
<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";
export default {
  name: "layout",
  components: {
    Header,
    Aside
  }
}
</script>
