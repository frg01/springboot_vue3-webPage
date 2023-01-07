<template>
  <div>
    <!-- 说明-->
    <!-- 先去掉, 这两个方法, 否则会报错-->
    <!-- @open="handleOpen"-->
    <!-- @close="handleClose"-->
    <el-menu
        style="width: 200px"
        default-active="2" class="el-menu-vertical-demo" >
      <el-sub-menu index="1-4">
        <template #title>选项 4</template>
        <el-menu-item index="1-4-1">选项 1</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="2">
        <i class="el-icon-menu"></i>
        <template #title>导航二</template>
      </el-menu-item>
      <el-menu-item index="3" disabled>
        <i class="el-icon-document"></i>
        <template #title>导航三</template>
      </el-menu-item>
      <el-menu-item index="4">
        <i class="el-icon-setting"></i>
        <template #title>导航四</template>
      </el-menu-item>
    </el-menu>
  </div>
</template>
<script>
export default {
  name: "Aside"
}
</script>
<style scoped>
</style>