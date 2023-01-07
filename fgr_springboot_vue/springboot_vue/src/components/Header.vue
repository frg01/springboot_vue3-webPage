<template>
<!--  样式设计-->
  <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display:
flex">
    <div style="width: 200px; padding-left: 30px; font-weight: bold; color: dodgerblue">后
      台管理</div>
    <div style="flex: 1"></div>
    <div style="width: 100px"><el-dropdown>
<span class="el-dropdown-link">
tom<i class="el-icon-arrow-down el-icon--right"></i>
</span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item>个人信息</el-dropdown-item>
          <el-dropdown-item>退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown></div>

  </div>
</template>
<script>
export default {
  name: "Header"
}
</script>